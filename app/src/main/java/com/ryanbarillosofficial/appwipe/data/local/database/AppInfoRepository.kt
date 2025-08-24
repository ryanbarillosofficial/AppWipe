package com.ryanbarillosofficial.appwipe.data.local.database

class AppInfoRepository(private val appInfoDao: AppInfoDao): AppInfoRepositoryInterface {
    override suspend fun insert(appInfoEntity: AppInfoEntity) = appInfoDao.insert(appInfoEntity)
    override suspend fun delete(appInfoEntity: AppInfoEntity) = appInfoDao.delete(appInfoEntity)
    override suspend fun getCount() = appInfoDao.getCount()
    override fun getAll() = appInfoDao.getAll()
    override fun getPackageNames() = appInfoDao.getPackageNames()

}