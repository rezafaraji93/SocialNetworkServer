package faraji.ir.di

import faraji.ir.data.repository.follow.FollowRepository
import faraji.ir.data.repository.follow.FollowRepositoryImpl
import faraji.ir.data.repository.post.PostRepository
import faraji.ir.data.repository.post.PostRepositoryImpl
import faraji.ir.data.repository.user.UserRepository
import faraji.ir.data.repository.user.UserRepositoryImpl
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

    single<FollowRepository> {
        FollowRepositoryImpl(get())
    }

    single<PostRepository> {
        PostRepositoryImpl(get())
    }
}