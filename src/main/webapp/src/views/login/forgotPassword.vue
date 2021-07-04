<template>
  <div class="login-container">
    <el-form
      class="login-form"
      autocomplete="on"
      :model="loginForm"
      :rules="loginRules"
      ref="loginForm"
      label-position="left"
      style="margin: 1% auto;"
    >
      <div class="title-container">
        <h4 class="title" style="margin: auto auto 20px auto;">忘记密码</h4>
      </div>
      <el-form-item prop="username">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          name="username"
          type="text"
          v-model="loginForm.username"
          autocomplete="on"
          placeholder="账号"
          style="width: 365px;margin-left: 15px;"
        />
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          name="password"
          :type="userPasswordType"
          @keyup.enter.native="handleLogin"
          v-model="loginForm.password"
          autocomplete="on"
          placeholder="密码"
          style="width: 365px;margin-left: 15px;"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>

      <el-link type="primary" style="margin:10px 0px 25px 25px;  color:#FFF;">
        <a href="./#/login">返回登录</a>
      </el-link>
      <br />
      <br />
      <el-button
        type="primary"
        style="width:100%"
        :loading="loading"
        @click.native.prevent="handleLogin"
      >修改密码</el-button>
    </el-form>
  </div>
</template>

<script>
import { checkUsername, getDepartment } from "@/api/common";
import { forgotPassword } from "@/api/login";
import { Message, MessageBox } from "element-ui"

export default {
  name: "registerDoctor",
  data() {
    var valideteUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("用户名称不能为空"));
      }
      checkUsername(value).then(response => {
        if (!response.data.data) {
          callback(new Error("用户名不存在，请更换用户名重试"));
        } else {
          callback();
        }
      });
    };
    const validateuserPassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error("管理员密码长度应大于6"));
      } else {
        callback();
      }
    };
    return {
      departmentOptions: undefined,
      loginForm: {
        username: "",
        password: ""
      },
      loginRules: {
        username: [
          { required: true, validator: valideteUsername, trigger: "blur" },
        ],
        password: [
          { required: true, validator: validateuserPassword, trigger: "blur" },
        ]
      },
      userPasswordType: "password",
      loading: false
    };
  },
   created() {
  },
  methods: {
    showPwd() {
      if (this.userPasswordType === "password") {
        this.userPasswordType = "";
      } else {
        this.userPasswordType = "password";
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          forgotPassword(this.loginForm).then(response => {
            if (response.data.status === 200) {
               Message({
                message: "修改成功，即将跳转到登录界面",
                type: "success",
                duration: 5 * 1000
              });
              this.loading = false;
              setTimeout(() => {
                window.open("./#/login");
              }, 3000);
            } else {
              this.msgError(response.msg);
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
$bg: #2d3a4b;
$light_gray: black;

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 20px;
    width: 85%;
    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: #fff !important;
      }
    }
  }
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: $bg;
  .login-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 520px;
    padding: 35px 35px 15px 35px;
    margin: 120px auto;
  }
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;
    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
    &_login {
      font-size: 20px;
    }
  }
  .title-container {
    position: relative;
    .title {
      font-size: 26px;
      font-weight: 400;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
