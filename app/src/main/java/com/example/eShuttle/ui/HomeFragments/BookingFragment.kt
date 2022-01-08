package com.example.eShuttle.ui.HomeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.eShuttle.R
import com.example.eShuttle.api.HomeApiInterface
import com.example.eShuttle.base.BaseFragment
import com.example.eShuttle.databinding.FragmentBookingBinding
import com.example.eShuttle.models.BookingModel
import com.example.eShuttle.repository.UserRepository
import com.example.eShuttle.utils.DatePickerFragment
import com.example.eShuttle.viewModels.UserViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import android.text.Editable

import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.example.eShuttle.utils.Resource


class BookingFragment : BaseFragment<
        UserViewModel,
        FragmentBookingBinding,
        UserRepository>(){

    //Override the base fragment methods
    override fun getViewModel() = UserViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBookingBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        // retrieve your saved token from datastore
        val token = runBlocking { userPreferences.jwt_authToken.first() }

        println("token JJJJJJJ : $token")
        //create api instance
        val api  = retrofitInstance.buildApi(HomeApiInterface::class.java, token)
        return UserRepository(api)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Time schedules
        val timeSchedules = resources.getStringArray(R.array.time)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, timeSchedules)
        binding.timeSchedules.setAdapter(arrayAdapter)

        //Seats to book
        val seatsToBook = resources.getStringArray(R.array.seatsToBook)
        val mySeatsToBookArr = ArrayAdapter(requireContext(), R.layout.drop_down_item, seatsToBook)
        binding.noOfSeats.setAdapter(mySeatsToBookArr)

        //category
        val category = resources.getStringArray(R.array.category)
        val myCategoryAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, category)
        binding.vehicleCategory.setAdapter(myCategoryAdapter)

        //who to pay arrayAdapter
        val whoToPay = resources.getStringArray(R.array.paymentMethod)
        val whoToPayAdapter = ArrayAdapter(requireContext(),R.layout.drop_down_item,whoToPay)
        binding.whoToPay.setAdapter(whoToPayAdapter)

        //Get date from the user DatePicker Fragment
        binding.apply {
            pickADate.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager


                //setFragment result listener
                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                ){
                    resultKey,bundle -> if (resultKey =="REQUEST_KEY"){
                        val date = bundle.getString("SELECTED_DATE")

                    //update UI
                    pickedDate.setText(date)
                }
                }

                // show
                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
            }
        }
        //<<<<<<<<<Handle api call while filling no of seats to get price>>>>>>>>>>>>>>>>>>
        binding.noOfSeats.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                // binding views
                val fromAddress = binding.fromAddress.text.toString()
                println(fromAddress)
                val toAddress = binding.toAddress.text.toString()
                val travelTime = binding.timeSchedules.text.toString()
                val travelDate = binding.pickedDate.text.toString()
                val noOfSeats = binding.noOfSeats.text.toString().toInt()
                val vehicleCategory = binding.vehicleCategory.text.toString()
                val whoIsPaying = binding.whoToPay.text.toString()

                //route Id
                val routeId = fromAddress+toAddress
                val travellingTime =travelTime+travelDate
                // send data to the backend
                if (s.isNotEmpty() && s.length ==1 || s.length==2) {
                    when{
                        fromAddress.isEmpty()->{
                        Toast.makeText(requireContext(), "Please enter departure address!", Toast.LENGTH_SHORT).show()
                        }
                        toAddress.isEmpty()->{
                            Toast.makeText(requireContext(), "Please enter destination address!", Toast.LENGTH_SHORT).show()
                        }
                        travelDate.isEmpty() ->{
                            binding.editTextSelectedDate.error = "Please select travel date"
                        }
                        travelTime.isEmpty()->{
                            binding.textInputTime.error ="Please select travel time"
                        }else ->{

                        val bookingModel = BookingModel(vehicleCategory,noOfSeats,fromAddress,toAddress,travellingTime)
                        viewModel.bookRide(bookingModel)

                        //Observe the changes
                        viewModel.bookRide.observe(viewLifecycleOwner, Observer { response ->
                            when(response){
                                is Resource.Success ->{

                                    //TODO update UI by getting the computed price
                                }
                            }
                        })
                        }
                    }

                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {

                // TODO Auto-generated method stub
            }
        })

        //<<<<<<<<<END of partial post >>>>>>>>>>>>

        //<<<<<<<<< Handle Full Booking  start >>>>>>>>>>>
        binding.btnSubmitBooking.setOnClickListener {



        }
        //<<<<<<<<<<<<END of full booking >>>>>>>>>

        //<<<<<<<< Get computed price start >>>>>>>>>
        fun getComputedPrice(){
            // TODO updateUI with the computed price
        }
        //<<<<<<<< End of get Computed price >>>>>>


        //navigate back
        binding.goBack.setOnClickListener {
            findNavController().navigate(
                R.id.action_bookingFragment_to_homeFragment
            )
        }

    }




}