package personal.ivan.higgshomework.view_model

import androidx.lifecycle.ViewModel
import personal.ivan.higgshomework.repository.GitHubRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: GitHubRepository) : ViewModel() {
}