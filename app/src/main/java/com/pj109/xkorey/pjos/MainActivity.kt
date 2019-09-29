package com.pj109.xkorey.pjos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pj109.xkorey.pjos.ui.image.SelfImageFragment
import com.pj109.xkorey.pjos.ui.login.SignInViewModel
import com.pj109.xkorey.pjos.ui.maze.MazeFragment
import com.pj109.xkorey.pjos.ui.media.MediaFragment
import com.pj109.xkorey.share.util.consume
import com.pj109.xkorey.share.util.inTransaction
import com.pj109.xkorey.share.util.viewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    companion object {
        private const val FRAGMENT_ID = R.id.fragment_container
        fun starterIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
            }
        }
    }

    @Inject
    lateinit var signInViewModel: SignInViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var currentFragment: MainNavigationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signInViewModel=viewModelProvider(viewModelFactory)

        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_image -> consume {
                    replaceFragment(SelfImageFragment())
                }
                R.id.navigation_media-> consume {
                    replaceFragment(MediaFragment())
                }
                R.id.navigation_maze-> consume {
                    replaceFragment(MazeFragment())
                }
                else -> false
            }
        }

        navigation.setOnNavigationItemReselectedListener {}

        if (savedInstanceState == null) {
            // default fragment
            // todo: config
            replaceFragment(SelfImageFragment())
        } else {
            // Find the current fragment
            currentFragment =
                supportFragmentManager.findFragmentById(FRAGMENT_ID) as? MainNavigationFragment
                    ?: throw IllegalStateException("Activity recreated, but no fragment found!")
        }
    }

    private fun <F> replaceFragment(fragment: F) where F : Fragment, F : MainNavigationFragment {
        supportFragmentManager.inTransaction {
            currentFragment = fragment
            replace(FRAGMENT_ID, fragment)
        }
    }


    override fun onBackPressed() {
        if (!currentFragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        currentFragment.onUserInteraction()
    }

}
