package woojin.projects.starbucksapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import woojin.projects.starbucksapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val homeData = readData(requireContext()) ?: return

        binding.appBarTitleTextView.text =
            getString(R.string.appbar_title_text, homeData.user.nickname)
        binding.starCountTextView.text =
            getString(
                R.string.appbar_star_title,
                homeData.user.starCount,
                homeData.user.totalCount
            )
        binding.progressBar.progress = homeData.user.starCount
        binding.progressBar.max = homeData.user.totalCount

        //Log.e("testt", "" + homeData.appbarImage)

        Glide.with(binding.appBarImageView)
            .load(homeData.appbarImage)
            .into(binding.appBarImageView)
    }
}