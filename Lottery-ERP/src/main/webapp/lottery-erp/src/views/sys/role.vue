<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="20">
          <el-input
            v-model="searchModel.roleName"
            placeholder="角色名称"
            clearable
          ></el-input>

          <el-button
            @click="getRoleList()"
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
      <el-table :data="roleList" stripe style="width: 100%">
        <el-table-column type="index" label="序号" width="80" align="center">
          <!-- （pageNo - 1)  * pageSize + index + 1-->
          <template slot-scope="scope">
            {{
              (searchModel.pageNo - 1) * searchModel.pageSize + scope.$index + 1
            }}
          </template>
        </el-table-column>

        <el-table-column
          prop="id"
          label="角色ID"
          width="200"
          align="center"
        ></el-table-column>
        <el-table-column
          prop="name"
          label="角色名称"
          width="260"
          align="center"
        >
        </el-table-column>
        <el-table-column prop="roleDesc" label="角色描述" align="center">
        </el-table-column>

        <el-table-column prop="status" label="角色状态" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 1" type="success">启用</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="180" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="openEditUI(scope.row.roleId)"
            ></el-button>

            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="deleteRole(scope.row)"
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

    <!-- 角色信息编辑对话框 -->
    <el-dialog
      @close="clearFrom"
      :title="title"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="roleForm" :rules="rules" ref="roleFormRef">
        <el-form-item
          label="角色名称"
          prop="roleName"
          :label-width="formLabelWidth"
        >
          <el-input v-model="roleForm.roleName" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          label="角色描述"
          prop="roleDesc"
          :label-width="formLabelWidth"
        >
          <el-input v-model="roleForm.roleDesc" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          label="权限设置"
          prop="menuIdList"
          :label-width="formLabelWidth"
        >
          <el-tree
            :data="menuList"
            :props="menuProps"
            show-checkbox
            node-key="menuId"
            ref="menuRef"
            style="width: 80%"
            default-expand-all
          ></el-tree>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRole">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>



<script>
//这里可以导入其他文件(比如:组件，工具 js，第三方插件 js，json文件，图片文件等等)
//例如:import 《组件名称》 from '《组件路径》';
import roleApi from '@/api/roleManage'
import menuApi from '@/api/menuManage'
export default {
  //import 引入的组件需要注入到对象中才能使用
  components: {},
  props: {},
  data () {
    return {
      menuList: [],
      menuProps: {
        children: 'children',
        label: 'title'
      },

      formLabelWidth: '130px',
      roleForm: {},
      dialogFormVisible: false,
      title: "",
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 10,
      },
      roleList: [],
      rules: {
        roleName: [
          { required: true, message: '请输角色名称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        roleDesc: [
          { required: false, message: '请输入角色描述', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],

      }

    }
  },

  //方法集合 
  methods: {
    getAllMenu () {
      menuApi.getAllMenu().then(resp => {
        this.menuList = resp.data

      })
    },
    getRoleList () {
      roleApi.getRoleList(this.searchModel).then((response) => {
        this.roleList = response.data.rows
        this.total = response.data.total
        console.log(response.data)
      })
    },

    deleteRole (role) {
      console.log(role)
      this.$confirm(`确认删除角色 【${role.roleName}】 `, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        roleApi.deleteRoleById(role.roleId).then(response => {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.getRoleList()
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    saveRole () {
      // 触发表单验证
      this.$refs.roleFormRef.validate((valid) => {
        if (valid) {
          let checkedKeys = this.$refs.menuRef.getCheckedKeys()
          let halfCheckedKeys = this.$refs.menuRef.getHalfCheckedKeys()
          this.roleForm.menuIdList = checkedKeys.concat(halfCheckedKeys)
          console.log(this.roleForm.menuIdList)

          // 提交请求给后台
          roleApi.saveRole(this.roleForm).then(response => {
            // 成功提示
            this.$message({
              message: response.message,
              type: 'success'
            })

            //关闭对话框
            this.dialogFormVisible = false

            //刷新表格
            this.getRoleList()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })


    },
    clearFrom () {
      this.roleForm = {}
      this.$refs.menuRef.setCheckedKeys([])
      this.$refs.roleFormRef.clearValidate()
    },
    openEditUI (id) {
      if (id == null) {
        this.title = "新增角色"
      }
      else {
        this.title = "修改角色"

        roleApi.getRoleById(id).then(response => {
          this.$refs.menuRef.setCheckedKeys(response.data.menuIdList)
          this.roleForm = response.data
        })
      }
      this.dialogFormVisible = true
    },
    handleSizeChange (pageSize) {
      this.searchModel.pageSize = pageSize
      this.getRoleList()
    },
    handleCurrentChange (pageNo) {
      this.searchModel.pageNo = pageNo
      this.getRoleList()
    },



  },
  //生命周期 - 创建完成(可以访问当前 this 实例)
  created () {
    this.getRoleList()
    // this.getAllMenu()
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
