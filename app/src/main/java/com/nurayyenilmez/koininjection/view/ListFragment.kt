package com.nurayyenilmez.koininjection.view

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nurayyenilmez.koininjection.databinding.FragmentListBinding
import com.nurayyenilmez.koininjection.model.CryptoModel
import com.nurayyenilmez.koininjection.viewModel.CryptoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment() ,RecyclerViewAdapter.Listener {

    private var _binding:FragmentListBinding?=null
    private val binding get() =_binding!!

    private var cryptoAdapter=RecyclerViewAdapter(arrayListOf(),this)
    val viewModel by viewModel<CryptoViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentListBinding.inflate(inflater,container,false)
        val view=binding.root
        return view

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager=layoutManager
       // viewModel=ViewModelProvider(this).get(CryptoViewModel::class.java)
        viewModel.getDataFromAP()

        observeLiveData()

    }
    private fun observeLiveData(){
        viewModel.cryptpList.observe(viewLifecycleOwner, Observer { cryptos->
        cryptos?.let {
            binding.recyclerView.visibility=View.VISIBLE
            cryptoAdapter= RecyclerViewAdapter(ArrayList(cryptos.data?: arrayListOf()),this@ListFragment)
            binding.recyclerView.adapter=cryptoAdapter
        }

        })
        viewModel.cryptoError.observe(viewLifecycleOwner, Observer { error->
            error?.let {
                if (it.data==true){
                    binding.cryptoErrorText.visibility=View.VISIBLE
                    binding.recyclerView.visibility=View.GONE
                }else{
                    binding.cryptoErrorText.visibility=View.GONE
                }
            }
        })
        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it.data==true){
                    binding.cryptoProgressBar.visibility=View.VISIBLE
                    binding.recyclerView.visibility=View.GONE
                    binding.cryptoErrorText.visibility=View.GONE
                }else{
                    binding.cryptoProgressBar.visibility=View.GONE

                }
            }

        })

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(requireContext(),"Clicked on :${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }
}

