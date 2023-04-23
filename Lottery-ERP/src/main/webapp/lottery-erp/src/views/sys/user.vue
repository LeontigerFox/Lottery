<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchModel.username"
            placeholder="用户名"
            clearable
          ></el-input>
          <el-input
            v-model="searchModel.phone"
            placeholder="电话"
            clearable
          ></el-input>
          <el-button
            @click="getUserList"
            type="primary"
            round
            icon="el-icon-search"
            >查询</el-button
          >
        </el-col>
        <el-col :span="4" align="right">
          <el-button
            @click="openEditUI(null)"
            type="primary"
            icon="el-icon-plus"
            circle
          ></el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 结果列表 -->
    <el-card>
      <el-table :data="userList" stripe style="width: 100%">
        <el-table-column type="index" label="序号" width="80" align="center">
          <!-- （pageNo - 1)  * pageSize + index + 1-->
          <template slot-scope="scope">
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>

        <el-table-column
          prop="username"
          label="姓名"
          width="150"
          align="center"
        ></el-table-column>
        <el-table-column prop="phone" label="电话" width="150" align="center">
        </el-table-column>
        <el-table-column
          prop="status"
          label="用户状态"
          width="150"
          align="center"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1" type="success">启用</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>

        <el-table-column
          prop="status"
          label="用户权限"
          width="150"
          align="center"
        >
          <template>
            <!-- <el-tag v-if="scope.row.roleId == 2">管理员</el-tag> -->
            <el-tag>管理员</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="email" label="邮箱" width="180" align="center">
        </el-table-column>
        <el-table-column prop="uid" label="用户UID" align="center">
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="openEditUI(scope.row.id)"
            ></el-button>

            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="deleteUser(scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page.sync="searchModel.pageNo"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="searchModel.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <!-- 用户信息编辑对话框 -->
    <el-dialog
      @close="clearFrom"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="userForm" :rules="rules" ref="userFormRef">
        <el-form-item
          label="用户名"
          prop="username"
          :label-width="formLabelWidth"
        >
          <el-input v-model="userForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          v-if="userForm.id == null || userForm.id == undefined"
          label="登陆密码"
          prop="password"
          :label-width="formLabelWidth"
        >
          <el-input
            v-model="userForm.password"
            autocomplete="off"
            type="password"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="联系电话"
          prop="phone"
          :label-width="formLabelWidth"
        >
          <el-input v-model="userForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户状态" :label-width="formLabelWidth">
          <el-switch
            v-model="userForm.status"
            :active-value="1"
            :inactive-value="0"
          >
          </el-switch>
        </el-form-item>

        <el-form-item
          label="电子邮件"
          prop="email"
          :label-width="formLabelWidth"
        >
          <el-input v-model="userForm.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveUser">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>



<script>
//这里可以导入其他文件(比如:组件，工具 js，第三方插件 js，json文件，图片文件等等)
//例如:import 《组件名称》 from '《组件路径》';
import userApi from '@/api/userManage'
import roleApi from '@/api/roleManage'
export default {
  //import 引入的组件需要注入到对象中才能使用
  components: {},
  props: {},
  data () {
    var checkEmail = (rule, value, callback) => {
      var reg = /^[A-Za-z0-9]+([_\.][A-Za-z0-9]+)*@([A-Za-z0-9\-]+\.)+[A-Za-z]{2,6}$/
      if (!value) {
        return callback()
      } else {
        if (!reg.test(value)) {
          return callback(new Error('邮箱格式错误'))
        }
        callback()
      }
    }
    var checkPhone = (rule, value, callback) => {
      var reg = /^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/
      if (!value) {
        return callback()
      } else {
        if (!reg.test(value)) {
          return callback(new Error('联系电话格式错误'))
        }
        callback()

      }
    }

    //这里存放数据

    return {
      roleList: [],
      formLabelWidth: '130px',
      userForm: {
        roleIdList: []
      },
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },

      userList: [],
      rules: {
        username: [
          { required: true, message: '请输用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur' }
        ],
        email: [
          { required: false, message: '请输入电子邮件', trigger: 'blur' },
          { validator: checkEmail, required: false, trigger: 'blur' }
        ],
        phone: [
          { required: false, message: '请输入联系电话', trigger: 'blur' },
          { validator: checkPhone, required: false, trigger: 'blur' }
        ],
      }

    }
  },

  //方法集合
  methods: {
    getAllRoleList () {
      roleApi.getAllRoleList().then(resp => {
        this.roleList = resp.data
      })
    },

    deleteUser (sysUser) {
      console.log(sysUser)
      this.$confirm(`确认删除用户 【${sysUser.username}】 `, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        userApi.deleteUserById(sysUser.id).then(response => {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.getUserList()
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    saveUser () {
      // 触发表单验证
      this.$refs.userFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          userApi.saveUser(this.userForm).then(response => {
            // 成功提示
            this.$message({
              message: response.message,
              type: 'success'
            })

            //关闭对话框
            this.dialogFormVisible = false

            //刷新表格
            this.getUserList()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })


    },
    clearFrom () {
      this.userForm = {
        roleIdList: []
      }
      this.$refs.userFormRef.clearValidate()
    },
    openEditUI (id) {
      if (id == null) {
        this.title = "新增用户"
      }
      else {
        this.title = "修改用户"
        // 根据用户查询id
        userApi.getUserById(id).then(response => {
          this.userForm = response.data
        })
      }
      this.dialogFormVisible = true
    },
    handleSizeChange (pageSize) {
      this.searchModel.pageSize = pageSize
      this.getUserList()
    },
    handleCurrentChange (pageNo) {
      this.searchModel.pageNo = pageNo
      this.getUserList()
    },

    getUserList () {
      userApi.getUserList(this.searchModel).then(response => {
        this.userList = response.data.rows
        this.total = response.data.total
      })
    }


  },
  //生命周期 - 创建完成(可以访问当前 this 实例)
  created () {
    this.getUserList()
    this.getAllRoleList()
  },



}
</script>
<style >
#search .el-input {
  width: 200px;
  margin-right: 12px;
}

.el-dialog .el-input {
  width: 80%;
}
</style>
