package com.lamnguyen.zalo.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.services.StompSocketService
import com.lamnguyen.zalo.ui.contract.headers.ContactHeaderFragment
import com.lamnguyen.zalo.ui.main.fragments.MainFragment
import com.lamnguyen.zalo.ui.main.viewmodels.MessageFragmentViewModel
import com.lamnguyen.zalo.ui.main.viewmodels.NavigationViewModel
import com.lamnguyen.zalo.ui.message.headers.MessageHeaderFragment
import com.lamnguyen.zalo.utils.helpers.TokenHelper


class MainActivity : AppCompatActivity() {
    private lateinit var navigationViewModel: NavigationViewModel
    private lateinit var headers: List<Fragment>
    private var oldPageIndex = 0
    private val stompServiceContext = StompSocketService.StompSocketServiceContext()
    private lateinit var messageFragmentViewModel: MessageFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        askNotificationPermission()

        navigationViewModel = ViewModelProvider(this)[NavigationViewModel::class.java]

        setupHeader()

        supportFragmentManager.commit {
            replace(R.id.frame_main, MainFragment())
        }

        messageFragmentViewModel = ViewModelProvider(this)[MessageFragmentViewModel::class]
    }


    private val connection = StompSocketService.initConnection(stompServiceContext) {
        it.subscribeDestinationMessage { message ->
            messageFragmentViewModel.addMessage(message)
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, StompSocketService::class.java).also { intent ->
            bindService(intent, connection, BIND_AUTO_CREATE)
            startService(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        if (stompServiceContext.bound) {
            unbindService(connection)
            stompServiceContext.bound = false
        }
    }


    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(
                this,
                "FCM SDK (and your app) can post notifications.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                "Inform user that that your app will not show notifications.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun setupHeader() {
        headers = listOf(
            MessageHeaderFragment(),
            ContactHeaderFragment(),
            ContactHeaderFragment(),
            ContactHeaderFragment(),
            ContactHeaderFragment(),
        )

        headers.forEachIndexed { index, header ->
            supportFragmentManager.commit {
                add(R.id.fragment_header, header)
                if (index != 0)
                    hide(header)
            }
        }

        navigationViewModel.pageIndexLiveData.observe(this) { pageIndex ->
            supportFragmentManager.commit {
                if (oldPageIndex == pageIndex) return@observe
                hide(headers[oldPageIndex])
                show(headers[pageIndex])
                oldPageIndex = pageIndex
            }
        }
    }
}