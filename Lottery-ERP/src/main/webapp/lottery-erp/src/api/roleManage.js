import request from '@/utils/request'



export default {
    getRoleList (searchModel) {
        return request({
            url: '/role/list',
            method: 'get',
            params: {
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                roleName: searchModel.roleName,
            }
        })
    },

    addRole (role) {
        return request({
            url: '/role/addRole',
            method: 'post',
            data: role
        })
    },

    getRoleById (id) {
        return request({
            url: `/role/${id}`,
            method: 'get',
        })
    },

    saveRole (role) {
        if (role.roleId == null || role.roleId == undefined) {
            return this.addRole(role)
        }
        else {
            return this.updateRole(role)
        }
    },

    updateRole (role) {
        return request({
            url: '/role',
            method: 'put',
            data: role
        })
    },

    deleteRoleById (id) {
        return request({
            url: `/role/${id}`,
            method: 'delete'
        })
    },

    getAllRoleList () {
        return request({
            url: '/role/all',
            method: 'get',

        })
    },
}