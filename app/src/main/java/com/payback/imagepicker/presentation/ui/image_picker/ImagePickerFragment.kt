package com.payback.imagepicker.presentation.ui.image_picker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.payback.imagepicker.databinding.FragmentImagePickerBinding
import com.payback.imagepicker.domain.model.Image
import com.payback.imagepicker.manager.utilities.*
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class ImagePickerFragment : Fragment() {

    private lateinit var imagePickerBinding: FragmentImagePickerBinding
    val imagePickerViewModel: ImagePickerViewModel by viewModels()

    private lateinit var imagePickerAdapter: ImagePickerAdapter
    private lateinit var imageListOffline :ArrayList<Image>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        imagePickerBinding = FragmentImagePickerBinding.inflate(inflater, container, false)

        observeImageList()
        observeSearchText()
        observeImageClicked()
        observeSearchResultNotFound()


        return imagePickerBinding.root
    }

    private fun observeSearchResultNotFound() {
        imagePickerViewModel.observeNotFound.observe(viewLifecycleOwner,EventObserver{
            imagePickerBinding.llImagePickerNotFoundContainer.visibility = VISIBLE
        })
    }


    private fun observeImageClicked() {
        imagePickerViewModel.observeImageClicked.observe(
            viewLifecycleOwner,
            EventObserver { imageObject ->
                val action =
                    ImagePickerFragmentDirections.actionImagePickerToImageDetailsDialog(
                        imageObject
                    )
                showConfirmDialog(action)
            })
    }


    private fun observeImageList() {
        imagePickerViewModel.observeImageListData.observe(
            viewLifecycleOwner,
            EventObserver { imageList ->
                imageListOffline = imageList
                Log.e("3254234324", "initializeImageList: ${imageList.size}" )

                initializeImageList(imageList)
            })
    }

    private fun observeSearchText() {
        imagePickerBinding.apply {
            searchView.getQueryTextChangeObservable()
                .debounce(Constants.DELAY_SMALL, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    imagePickerBinding.llImagePickerNotFoundContainer.visibility = GONE
                    imagePickerViewModel.filterSearchKeyWord(result)
                }
        }

    }

    private fun initializeImageList(imageList: ArrayList<Image>) {
        imagePickerAdapter = ImagePickerAdapter(imageList, imagePickerViewModel)
        imagePickerBinding.apply {
            rvImageList.setHasFixedSize(true)
            rvImageList.layoutManager = GridLayoutManager(requireContext(), 2)
            rvImageList.adapter = imagePickerAdapter
            recyclerAnimationExtension(rvImageList)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(Constants.GSON_KEY, convertFromImageListToString(imageListOffline))
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        if(savedInstanceState?.containsKey(Constants.GSON_KEY) == true) {
            val string = savedInstanceState.get(Constants.GSON_KEY) as String
            val theList = convertFromStringToImageList(string)
            imageListOffline = theList
            initializeImageList(theList)
        }
        super.onViewStateRestored(savedInstanceState)
    }


}