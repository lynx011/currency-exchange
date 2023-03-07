package com.example.data_binding.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data_binding.R
import com.example.data_binding.adapter.NewsAdapter
import com.example.data_binding.api_service.NewsApi
import com.example.data_binding.databinding.FragmentHomeBinding
import com.example.data_binding.model.Article
import com.example.data_binding.repository.NewsRepository
import com.example.data_binding.utils.ItemClickListener
import com.example.data_binding.view_model.NewsViewModel
import com.example.data_binding.view_model_factory.ViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = NewsApi.getInstance()
        val repository = NewsRepository(apiService)

        viewModel = ViewModelProvider(this,ViewModelFactory(repository))[NewsViewModel::class.java]

        binding.recView.apply {
            layoutManager = LinearLayoutManager(context)
            newsAdapter = NewsAdapter()
        }

        binding.newsAdapter = newsAdapter

        newsAdapter.onClickListener(object : ItemClickListener{
            override fun onClicked(article: Article) {
                Toast.makeText(requireContext(),article.title,Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.apply {
                    putString("title",article.title)
                    putString("desc",article.description)
                    putString("thumb",article.urlToImage)
                }
                findNavController().navigate(R.id.newsDetailsFragment,bundle)
            }

        })

        viewModel.articleList.observe(viewLifecycleOwner){ articleList ->
            if (articleList != null) {

            }
        }


    }

}