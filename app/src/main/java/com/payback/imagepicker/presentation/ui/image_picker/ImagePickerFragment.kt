package com.payback.imagepicker.presentation.ui.image_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.payback.imagepicker.databinding.FragmentImagePickerBinding
import com.payback.imagepicker.manager.utilities.Constants
import com.payback.imagepicker.manager.utilities.EventObserver
import com.payback.imagepicker.manager.utilities.getQueryTextChangeObservable
import com.payback.imagepicker.manager.utilities.showConfirmDialog
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class ImagePickerFragment : Fragment() {

    private lateinit var imagePickerBinding: FragmentImagePickerBinding
    private val imagePickerViewModel: ImagePickerViewModel by activityViewModels()
    private lateinit var imagePickerAdapter: ImagePickerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        imagePickerBinding = FragmentImagePickerBinding.inflate(inflater, container, false)

        observeImageList()
        observeSearchText()
        observeImageClicked()

        return imagePickerBinding.root
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
                imagePickerAdapter = ImagePickerAdapter(imageList, imagePickerViewModel)
                imagePickerBinding.apply {
                    rvImageList.setHasFixedSize(true)
                    rvImageList.layoutManager = GridLayoutManager(requireContext(), 2)
                    rvImageList.adapter = imagePickerAdapter
                }
            })
    }

    private fun observeSearchText() {
        imagePickerBinding.apply {
            searchView.getQueryTextChangeObservable()
                .debounce(Constants.DELAY_SMALL, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    imagePickerViewModel.filterSearchKeyWord(result)
                }
        }

    }


}