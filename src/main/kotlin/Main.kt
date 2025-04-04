package ir.alitayyeb


fun main() {

    val handler = GitHubApiHandler()

    while (true) {
        // input a str from stdin:
        val line = readlnOrNull() ?: continue
        val tokens = line.split("\\s+".toRegex())

        when (tokens[0]) {
            "search" -> {
                if (tokens.size < 2) {
                    println("Missing search keyword")
                    continue
                }
                try {
                    val user = handler.getUser(tokens[1])
                    if (user != null)
                        handler.printUser(user)
                    else
                        println("No user found with name ${tokens[1]}")
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }

            "cache" -> {
                if (tokens.size < 2) {
                    println("Missing command. list and search are available.")
                    continue
                }
                when (tokens[1]) {
                    "list" -> {
                        handler.printCachedUsers()
                    }

                    "search" -> {
                        if (tokens.size < 3) {
                            println("Missing search keyword. it can be '<username>' or 'repo <repo_name>'")
                            continue
                        }
                        val searchedUsername = tokens[2]
                        if (searchedUsername == "repo" && tokens.size > 3) {
                            val repos = handler.getCachedRepo(tokens[3])
                            if (repos.isNotEmpty())
                                for (repo in repos)
                                    handler.printRepo(repo)
                            else
                                println("No repository found with name ${tokens[3]} in the cache.")

                        } else {
                            val user = handler.getCachedUser(tokens[2])
                            if (user != null)
                                handler.printUser(user)
                            else
                                println("No user found with name ${tokens[2]} in the cache.")
                        }

                    }

                    else -> {
                        println("Unknown command. list and search are available.")
                    }
                }
            }

            "exit" -> break

            else -> println(
                "Invalid input. Valid inputs are:\n" +
                        "search <username>\n" +
                        "cache list\n" +
                        "cache search <username>\n" +
                        "cache search repo <repo_name>\n" +
                        "exit\n"
            )
        }
    }
}

