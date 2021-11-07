package faraji.ir.di

import com.google.gson.Gson
import faraji.ir.data.repository.activity.ActivityRepository
import faraji.ir.data.repository.activity.ActivityRepositoryImpl
import faraji.ir.data.repository.comment.CommentRepository
import faraji.ir.data.repository.comment.CommentRepositoryImpl
import faraji.ir.data.repository.follow.FollowRepository
import faraji.ir.data.repository.follow.FollowRepositoryImpl
import faraji.ir.data.repository.likes.LikesRepository
import faraji.ir.data.repository.likes.LikesRepositoryImpl
import faraji.ir.data.repository.post.PostRepository
import faraji.ir.data.repository.post.PostRepositoryImpl
import faraji.ir.data.repository.user.UserRepository
import faraji.ir.data.repository.user.UserRepositoryImpl
import faraji.ir.service.*
import faraji.ir.util.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {

    single {
        val client = KMongo.createClient().coroutine
        client.getDatabase(DATABASE_NAME)
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }

    single { UserService(get(), get()) }

    single<FollowRepository> {
        FollowRepositoryImpl(get())
    }

    single { FollowService(get()) }

    single<PostRepository> {
        PostRepositoryImpl(get())
    }

    single { PostService(get()) }

    single<LikesRepository> {
        LikesRepositoryImpl(get())
    }

    single { LikeService(get(), get(), get()) }

    single<CommentRepository> {
        CommentRepositoryImpl(get())
    }

    single { CommentService(get()) }

    single<ActivityRepository> {
        ActivityRepositoryImpl(get())
    }

    single {ActivityService(get(), get(), get())}

    single { Gson() }
}