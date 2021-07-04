<template>
  <div class="login-container">
    <el-form
      class="login-form"
      autocomplete="on"
      :model="loginForm"
      :rules="loginRules"
      ref="loginForm"
      label-position="left"
      style="margin: 5% auto;"
    >
      <div class="title-container">
        <h3 class="title" style="margin: 20px auto 20px auto;">登录</h3>
      </div>
      <el-form-item prop="userCode">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          name="userCode"
          type="text"
          v-model="loginForm.userCode"
          autocomplete="on"
          placeholder="账号"
          style="width: 365px;margin-left: 15px;"
        />
      </el-form-item>

      <el-form-item prop="userPassword">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          name="userPassword"
          :type="userPasswordType"
          @keyup.enter.native="handleLogin"
          v-model="loginForm.userPassword"
          autocomplete="on"
          placeholder="密码"
          style="width: 365px;margin-left: 15px;"
        />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>
      <el-link type="primary" style="margin:10px 0px 25px 25px;  color:#FFF;">
        <a href="./#/registerDoctor">医生注册</a>
      </el-link>
       <el-link type="primary" style="margin:10px 0px 25px 250px;  color:#FFF;">
        <a href="./#/registerPatients">患者注册</a>
      </el-link>
      <br />
      <br />
      <br />
      <el-button
        type="primary"
        style="width:100%"
        :loading="loading"
        @click.native.prevent="handleLogin"
      >登录</el-button>
      <br/>
      <br/>
      <el-link type="primary" style="margin:0px 20px 0px 0px; color:#FFF;"><a href="./#/forgotPassword">已有帐号，忘记密码？</a></el-link>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "registerPatients",
  data() {
    const validateuserCode = (rule, value, callback) => {
      if (validateuserCode == null) {
        callback(new Error("请输入正确的管理员用户名"));
      } else {
        callback();
      }
    };
    const validateuserPassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error("管理员密码长度应大于6"));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        userCode: "",
        userPassword: ""
      },
      loginRules: {
        userCode: [
          { required: true, trigger: "blur", validator: validateuserCode }
        ],
        userPassword: [
          { required: true, trigger: "blur", validator: validateuserPassword }
        ]
      },
      userPasswordType: "password",
      loading: false
    };
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
        if (valid && !this.loading) {
          this.loading = true;
          this.$store
            .dispatch("LoginByUsername", this.loginForm)
            .then(() => {
              this.loading = false;
              this.$router.push({ path: "/" });
            })
            .catch(() => {
              this.$message({
                showClose: true,
                message: "账号或密码错误",
                type: "error"
              });
              this.loading = false;
            });
        } else {
          return false;
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
    height: 47px;
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
