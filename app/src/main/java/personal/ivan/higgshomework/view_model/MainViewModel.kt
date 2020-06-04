package personal.ivan.higgshomework.view_model

import android.view.View
import androidx.lifecycle.ViewModel
import personal.ivan.higgshomework.repository.GitHubRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: GitHubRepository) : ViewModel() {

    // region UI Event - User List

    fun userOnClickListener(view: View) {

    }

    // endregion
}