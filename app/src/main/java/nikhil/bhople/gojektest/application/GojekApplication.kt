package nikhil.bhople.gojektest.application

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class GojekApplication: Application(), KodeinAware  {

    override val kodein: Kodein = Kodein.lazy {

    }
}