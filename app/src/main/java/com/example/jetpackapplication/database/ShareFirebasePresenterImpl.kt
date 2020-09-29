package com.example.jetpackapplication.database
//
//import com.example.core.rx.RxSchedulers
//import com.example.firestore.FirebaseState
//import com.example.firestore.getChatFromDataSnapshot
//import com.example.firestore.model.ChannelInfo
//import com.example.firestore.repository.ShareFirebasePresenter
//import com.example.firestore.repository.chat.ChatRepository
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.subjects.BehaviorSubject
//import javax.inject.Inject
//
///**
// * combine all user, all channel, all friend
// * */
//
//class ShareFirebasePresenterImpl @Inject constructor(
//    private val chatRepository: ChatRepository,
//    private val rxSchedulers: RxSchedulers
//) : ShareFirebasePresenter {
//
//    private val listChannels =
//        BehaviorSubject.createDefault<HashMap<String, ChannelInfo>>(hashMapOf())
//
//    private val compositeDisposable by lazy {
//        CompositeDisposable()
//    }
//
//    private fun getListChannelOfUser(myUid: String) {
//        chatRepository.getListChannels(myUid)
//            .subscribeOn(rxSchedulers.io())
//            .observeOn(rxSchedulers.androidThread())
//            .subscribe {
//                val channelInfo = it.childData.getChatFromDataSnapshot()
//                if (channelInfo != null) {
//                    when (it.childType) {
//                        FirebaseState.ADD -> {
//                            val listChannel = listChannels.value
//                            listChannel?.set(channelInfo.channelId, channelInfo)
//                            listChannel?.let { it1 -> listChannels.onNext(it1) }
//
//                            //get channel info detail
//                            getChannelInfo(channelInfo.channelId)
//                        }
//                        FirebaseState.CHANGED -> {
//                            val listChannel = listChannels.value
//                            listChannel?.set(channelInfo.channelId, channelInfo)
//                            listChannel?.let { it1 -> listChannels.onNext(it1) }
//                        }
//                        FirebaseState.REMOVED -> {
//                            val listChannel = listChannels.value
//                            listChannel?.remove(channelInfo.channelId)
//                            listChannel?.let { it1 -> listChannels.onNext(it1) }
//                        }
//                    }
//                }
//            }.let { compositeDisposable.add(it) }
//    }
//
//    private fun getChannelInfo(channelId: String) {
//        chatRepository.getChannelInfo(channelId)
//            .subscribeOn(rxSchedulers.io())
//            .observeOn(rxSchedulers.androidThread())
//            .subscribe {
//                val channelInfo = it.getChatFromDataSnapshot()
//                if (channelInfo != null) {
//                    val listChannel = listChannels.value
//                    listChannel?.set(channelInfo.channelId, channelInfo)
//                    listChannel?.let { it1 -> listChannels.onNext(it1) }
//                }
//            }.let { compositeDisposable.add(it) }
//
//    }
//
//    override fun getListChannels(channelId: String): BehaviorSubject<HashMap<String, ChannelInfo>> {
//        getListChannelOfUser(channelId)
//        return listChannels
//    }
//}