package com.nicoalex.todo.User

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicoalex.todo.network.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UserInfoViewModel : ViewModel(){
    private val repository = UserInfoRepository();

    private var _userInfo = MutableStateFlow<UserInfo?>(null);
    var userInfo = _userInfo.asStateFlow() ;

    fun refresh() {
        viewModelScope.launch {
            var data = repository.refresh();
            if(data != null) {
                _userInfo.value = data;
                userInfo = _userInfo;
            }
        }
    }

    fun updateAvatar(avatar: MultipartBody.Part) {
        viewModelScope.launch {
            var data = repository.updateAvatar(avatar);
            if(data != null) {
                _userInfo.value = data;
                userInfo = _userInfo;
            }
        }
    }

    fun updateUserInfo(user: UserInfo) {
        viewModelScope.launch {
            var data = repository.updateUserInfo(user);
            if(data != null) {
                _userInfo.value = data;
                userInfo = _userInfo;
            }
        }
    }
}