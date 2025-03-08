package ir.alitayyeb

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ir.alitayyeb.ir.alitayyeb.GitHubApi
import ir.alitayyeb.ir.alitayyeb.GithubUser
import ir.alitayyeb.ir.alitayyeb.Repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubApiHandler() {
    private val cachedUsers = HashMap<String, GithubUser>()

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    private val api = retrofit.create(GitHubApi::class.java)

    fun getUser(username: String): GithubUser? {
        // Check the cache first and return if the user exists in the cache:
        val cachedUser = cachedUsers[username]
        if (cachedUser != null) {
            return cachedUser
        }

        // Call the api:
        val user = api.getUser(username).execute().body()
        if (user != null) {
            val repos = api.getUserRepos(username).execute().body()
            if (repos != null) {
                user.repositories = repos
            }
            // Add the fetched user to the cache:
            cachedUsers[user.username] = user
        }

        return user
    }

    fun printUser(user: GithubUser) {
        printUserHeader(user)
        println("💻 List of public repositories:")
        println("===================================")
        for (repo in user.repositories) {
            printRepo(repo)
        }
    }

    fun printCachedUsers() {
        for (user in cachedUsers.values) {
            printUserHeader(user)
            println("💻 Number of public repositories: ${user.repositories.size}")
            println("===================================")
        }
    }

    fun getCachedUser(username: String): GithubUser? {
        return cachedUsers[username]
    }

    fun getCachedRepo(repoName: String): List<Repository> {
        val repos = ArrayList<Repository>()
        for (user in cachedUsers.values)
            for (repo in user.repositories)
                if (repo.name == repoName)
                    repos.add(repo)
        return repos
    }

    private fun printUserHeader(user: GithubUser) {
        println("🙋 ${user.username}")
        println("📢 followers: ${user.followers}")
        println("🔍 following: ${user.following}")
        println("📆 created at ${user.createdAt}")
    }

    fun printRepo(repo: Repository) {
        println("🟣 ${repo.name}")
        println("🌟 ${repo.stars} star")
        if (repo.description != null)
            println("📋 ${repo.description}")
        println("🔗 ${repo.url}")
        println("📆 created at ${repo.createdAt}")
        println("===================================")
    }

}


