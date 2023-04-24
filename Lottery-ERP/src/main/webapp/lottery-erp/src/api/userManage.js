import request from '@/utils/request'

export function login (data) {
    return request({
        URIError: '/user/login',
        method: 'post',
        data
    })
}

export default {
    getUserList (searchModel) {
        return request({
            url: '/user/list',
            method: 'get',
            params: {
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                username: searchModel.username,
                phone: searchModel.phone,
            }
        })
    },

    addUser (user) {
        return request({
            url: '/user/addUser',
            method: 'post',
            data: user
        })
    },

    getUserById (id) {
        return request({
            url: `/user/${id}`,
            method: 'get',
        })
    },

    saveUser (user) {
        if (user.id == null || user.id == undefined) {
            return this.addUser(user)
        }
        else {
            return this.updateUser(user)
        }
    },

    updateUser (user) {
        return request({
            url: '/user',
            method: 'put',
            data: user
        })
    },

    deleteUserById (id) {
        return request({
            url: `/user/${id}`,
            method: 'delete'
        })
    },

    getInfo () {
        return request({
            url: '/user/info',
            method: 'get',
        })
    }
}