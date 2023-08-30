package woojin.projects.starbucksapplication

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import woojin.projects.starbucksapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val homeData = readData(requireContext(), "home.json", Home::class.java) ?: return
        val menuData = readData(requireContext(), "menu.json", Menu::class.java) ?: return

        initAppBar(homeData)
        initRecommendMenuList(homeData, menuData)
        initFoodList(menuData)
        initBanner(homeData)
        initFloatingButton()
        //Log.e("HomeFragment", "" + homeData.appBarImage)


    }

    private fun initFloatingButton() {
        //ExtendedFloatingActionButton은 접혔다 늘렸다 할 수 있는 기능이 있음
        //기본 설정 shrink로 해 둠 으로써 첫 동작 시 문제 해결
        binding.floatingActionButton.shrink()

        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY == 0) {
                binding.floatingActionButton.shrink()
            } else {
                binding.floatingActionButton.extend()
            }
        }
    }

    private fun initFoodList(menuData: Menu) {
        binding.foodMenuList.titleTextView.text = getString(R.string.food_menu_title)

        menuData.coffee.forEach { menuItem ->
            binding.recommendMenuList.menuLayout.addView(
                //MenuView를 통해 동적으로 생성
                MenuView(requireContext()).apply {
                    setTitle(menuItem.name)
                    setImageUrl(menuItem.image)
                }
            )
        }
    }

    private fun initBanner(homeData: Home) {
        binding.bannerLayout.bannerImageView.apply {
            Glide.with(this)
                .load(homeData.banner.image)
                .into(this)
            this.contentDescription = homeData.banner.contentDescription
        }
    }

    private fun initRecommendMenuList(homeData: Home, menuData: Menu) {

        binding.recommendMenuList.titleTextView.text =
            getString(R.string.recommend_menu, homeData.user.nickname)

        menuData.food.forEach { menuItem ->
            binding.foodMenuList.menuLayout.addView(
                //MenuView를 통해 동적으로 생성
                MenuView(requireContext()).apply {
                    setTitle(menuItem.name)
                    setImageUrl(menuItem.image)
                }
            )
        }
    }

    private fun initAppBar(homeData: Home) {
        binding.appBarTitleTextView.text =
            getString(R.string.appbar_title_text, homeData.user.nickname)

        binding.starCountTextView.text =
            getString(
                R.string.appbar_star_title,
                homeData.user.starCount,
                homeData.user.totalCount
            )
        Glide.with(binding.appBarImageView)
            .load(homeData.appBarImage)
            .into(binding.appBarImageView)

        binding.progressBar.max = homeData.user.totalCount

        ValueAnimator.ofInt(0, homeData.user.starCount).apply {
            duration = 500
            addUpdateListener {
                binding.progressBar.progress = it.animatedValue as Int
            }
            start()
        }
    }
}
