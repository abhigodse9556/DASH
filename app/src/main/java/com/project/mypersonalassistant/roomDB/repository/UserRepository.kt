package com.project.mypersonalassistant.roomDB.repository

import com.project.mypersonalassistant.roomDB.DAO.UserDao
import com.project.mypersonalassistant.roomDB.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun register(user: User) = userDao.insertUser(user)

    suspend fun login(username: String, password: String): User? =
        userDao.login(username, password)

    suspend fun isUserExists(username: String): Boolean =
        userDao.getUserByUsernameOrEmail(username) != null
}