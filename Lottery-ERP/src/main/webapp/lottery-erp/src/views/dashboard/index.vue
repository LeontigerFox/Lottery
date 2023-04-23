// 源码（版权所有）：
<template>
  <div class="personalCenter">
    <el-header>个人中心</el-header>

    <div class="img" align="center">
      <el-avatar :size="100" :src="circleUrl"></el-avatar>
    </div>
    <!-- <div class="block" align="center">
      <span>{{ name }}</span>
    </div> -->

    <div align="center" class="home_input">
      <el-input
        type="text"
        v-model="userInfo.id"
        placeholder="用户 ID"
        disabled
        prefix-icon="el-icon-user"
        show-word-limit
      ></el-input>

      <el-input
        v-model="userInfo.uid"
        placeholder="用户 UID"
        disabled
        prefix-icon="el-icon-key"
        show-word-limit
      ></el-input>

      <el-input
        v-model="userInfo.username"
        placeholder="用户名"
        disabled
        prefix-icon="el-icon-user-solid"
        show-word-limit
      ></el-input>

      <el-input
        v-model="userInfo.phone"
        placeholder="联系方式"
        disabled
        prefix-icon="el-icon-phone"
        show-word-limit
      ></el-input>

      <el-input
        v-model="userInfo.email"
        placeholder="邮箱"
        disabled
        prefix-icon="el-icon-message"
        show-word-limit
      ></el-input>

      <el-input
        v-if="userInfo.roleId == 1"
        placeholder="超级管理员"
        disabled
        prefix-icon="el-icon-view"
        show-word-limit
      ></el-input>

      <el-input
        v-if="userInfo.roleId == 2"
        placeholder="管理员"
        disabled
        prefix-icon="el-icon-view"
        show-word-limit
      ></el-input>

      <el-input
        v-if="userInfo.roleId == 3"
        placeholder="超级管理员"
        disabled
        prefix-icon="el-icon-view"
        show-word-limit
      ></el-input>
    </div>
  </div>
</template>
<script>
import userApi from '@/api/userManage'

export default {
  data () {
    return {
      name: "Dashboard",
      circleUrl:
        'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png',
      sizeList: ['large'],
      userInfo: {
      },
      role: ""
    }
  },
  methods: {
    getUserInfo () {
      userApi.getInfo().then(res => {
        console.log(res)
        this.userInfo = res.data
      })
    }
  },

  mounted () {
    this.getUserInfo()
  }
}
</script>
<style>
.home_input {
  line-height: 50px;
}
input {
  text-align: center;
}

.el-header {
  line-height: 60px;
  text-align: center;
  background-color: #b3c0d1;
}
.el-aside {
  line-height: 44px;
  text-align: center;
  background-color: #d3dce6;
}
.el-main {
  line-height: 36px;
  text-align: center;
  background-color: #e9eef3;
}

.demo-basic--circle {
  align: "center";
  margin-top: 30px;
  margin-left: 150px;
}
.block {
  margin-left: 25px;
  font-weight: bold;
}
.text {
  font-size: 14px;
}
.item {
  margin-bottom: 18px;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 1368px;
  border-radius: 30px;
}
</style>

