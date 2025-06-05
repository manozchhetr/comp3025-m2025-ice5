package ca.georgiancollege.ice5

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ca.georgiancollege.ice5.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity()
{
   private lateinit var binding: ActivitySplashBinding
   override fun onCreate(savedInstanceState: Bundle?)
   {
      super.onCreate(savedInstanceState)
      binding = ActivitySplashBinding.inflate(layoutInflater)
      enableEdgeToEdge()
      setContentView(binding.root)
      ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
         val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
         v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
         insets
      }

      Handler(Looper.getMainLooper()).postDelayed({
         startActivity(Intent(this, MainActivity::class.java))
         finish()
      }, 3000) // 3000 milliseconds or 3 seconds

      val spinAnimation = AnimationUtils.loadAnimation(this, R.anim.spin)
      binding.spinnerIcon.startAnimation(spinAnimation)
   }
}