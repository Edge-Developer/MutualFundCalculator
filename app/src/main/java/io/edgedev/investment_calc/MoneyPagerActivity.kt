package io.edgedev.investment_calc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AnimationUtils
import android.widget.ImageView

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd

class MoneyPagerActivity : AppCompatActivity(), OnClickListener {
    private var actionBar: ActionBar? = null
    private var percentage: String? = null
    private var next: ImageView? = null
    private var previous: ImageView? = null
    private lateinit var viewPager: ViewPager
    private var mAdView: AdView? = null

    private var mMoneyList: List<Money>? = null
    private lateinit var mInterstitialAd: InterstitialAd
    private var incrementToShowAd = 1


    override fun onPause() {
        super.onPause()
        mAdView!!.pause()
    }

    override fun onResume() {
        super.onResume()
        mAdView!!.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdView!!.destroy()
    }
    inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            val money = mMoneyList!![position]
            return InvestmentDetail.newInstance(money.year!!)
        }

        override fun getCount(): Int {
            return mMoneyList!!.size
        }
        fun onPageSelected(position: Int) {
            toggleArrows(position)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_pager)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.interstitial_detail_activity)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

        val intent = intent
        val year = intent.getStringExtra(YEAR_KEY)
        percentage = intent.getStringExtra(PER_CENT_SUBTITLE_KEY)
        mMoneyList = Singleton.getInstance().moneyList

        actionBar = supportActionBar
        assert(actionBar != null)
        setSubtitle(year)
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        viewPager = findViewById(R.id.money_viewpager)
        val fragmentAdapter =MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter

        for (i in mMoneyList!!.indices) {
            if (mMoneyList!![i].year == year) {
                viewPager.currentItem = i
                break
            }
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                val money = mMoneyList!![position]
                setSubtitle(money.year!!)
                fragmentAdapter.onPageSelected(position)

            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        next = findViewById(R.id.next)
        previous = findViewById(R.id.previous)
        next!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wobble_right))
        previous!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wobble_left))
        next!!.setOnClickListener(this)
        previous!!.setOnClickListener(this)

        toggleArrows(viewPager.currentItem)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView!!.loadAd(adRequest)
    }

    private fun toggleArrows(currentItemIndex: Int) {
        val mViewPagerCount = viewPager.adapter!!.count
        if (currentItemIndex + 1 == mViewPagerCount) {
            next!!.clearAnimation()
            next!!.visibility = View.INVISIBLE

        } else if (currentItemIndex + 1 < viewPager.adapter!!.count && currentItemIndex + 1 > mViewPagerCount - 2) {
            next!!.visibility = View.VISIBLE
            next!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wobble_right))
            previous!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wobble_left))
        }
        if (currentItemIndex > 0 && currentItemIndex < 2) {
            previous!!.visibility = View.VISIBLE
            previous!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wobble_left))
            next!!.startAnimation(AnimationUtils.loadAnimation(this, R.anim.wobble_right))
        } else if (currentItemIndex == 0) {
            previous!!.clearAnimation()
            previous!!.visibility = View.INVISIBLE
        }
    }

    private fun setSubtitle(year: String) {
        val subtitle = getString(R.string.sub_format, year, "-", "@", percentage, "%")
        actionBar!!.subtitle = subtitle
    }

    override fun onClick(view: View) {
        val id = view.id
        val mViewPagerCurrentItem = viewPager.currentItem
        showInterstitialAd()
        if (id == R.id.next) {
            viewPager.currentItem = mViewPagerCurrentItem + 1
            toggleArrows(viewPager.currentItem)
        } else if (id == R.id.previous) {
            viewPager.currentItem = mViewPagerCurrentItem - 1
            toggleArrows(viewPager.currentItem)
        }
    }

    private fun showInterstitialAd() {
        if ((incrementToShowAd % 4) == 0 && mInterstitialAd.isLoaded) mInterstitialAd.show()
        incrementToShowAd++
    }

    companion object {

        val YEAR_KEY = "year_key"
        val PER_CENT_SUBTITLE_KEY = "per_cent_sub_key"

        fun newIntent(context: Context, year: String, sub_title: String): Intent {
            val intent = Intent(context, MoneyPagerActivity::class.java)
            intent.putExtra(YEAR_KEY, year)
            intent.putExtra(PER_CENT_SUBTITLE_KEY, sub_title)
            return intent
        }
    }
}
