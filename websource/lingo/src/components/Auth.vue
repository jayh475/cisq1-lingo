<template>
  <div class="page-container">
    <form novalidate class="md-layout " @submit.prevent="validateUser">
      <md-card class="md-layout-item">
        <md-card-header>
          <div class="md-title">Practice Lingo</div>
        </md-card-header>

        <md-card-content>
          <div class="md-layout-item">
            <md-field>
              <label>Username</label>
              <md-input name="user-name" id="user-name" v-model="form.username"/>
            </md-field>
            <md-field>
              <label>Password</label>
              <md-input name="pass-word" id="pass-word" v-model="form.password"/>
            </md-field>
            <md-field v-if="!isLoginForm">
              <label>Password</label>
              <md-input name="pass-word-check" id="pass-word-check" v-model="form.passWordCheck"/>
            </md-field>
            <md-field v-if="!isLoginForm">
              <label>firstname</label>
              <md-input name="firstname" id="first-name" v-model="form.firstname"/>
            </md-field>
            <md-field v-if="!isLoginForm">
              <label>lastname</label>
              <md-input name="last-name" id="last-name" v-model="form.lastName"/>
            </md-field>

            <md-label style="color: darkred">{{errorMsg}}</md-label>
          </div>
        </md-card-content>

        <md-progress-bar md-mode="indeterminate" v-if="sending" />

        <md-card-actions style="display:flex; justify-content: space-between">
          <md-button type="submit" class="md-primary" @click="isLoginForm = !isLoginForm">{{(!isLoginForm) ? "Already registered?" : "Create a new account?"}}</md-button>
          <md-button type="submit" class="md-primary" v-if="!isLoginForm" @click="userRegister">Register</md-button>
          <md-button type="submit" class="md-primary" v-if="isLoginForm" @click="userLogin">Login</md-button>
        </md-card-actions>
      </md-card>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Auth",
  data(){
    return {
      form: {},
      isLoginForm: true,
      errorMsg: ''
    }
  },
  methods: {
    validateUser(){
      this.errorMsg = '';
      try{
        if (!this.form.password || !this.form.username){
          this.errorMsg = "Please fill in an username and password"
          return false;
        }
      }catch (ignored) {console.log(ignored); return false}

      if (!this.isLoginForm){
        try{
          if (this.form.password.length < 5){
            this.errorMsg = "Password not long enough"
            return false;
          }
        }catch (ignored) {console.log(ignored); return false}

        try{
          if (this.form.password !== this.form.passWordCheck){
            this.errorMsg = "Passwords don't match"
            return false;
          }
        }catch (ignored) {console.log(ignored); return false}

      }

      return true;
    },
    userRegister(){
      if (!this.validateUser()) return;
      axios.post(`${this.$restip}/register`, {
        username: this.form.username,
        password: this.form.password,
        firstName: this.form.firstname,
        lastName: this.form.lastName
      })
          .then((res) => {
            console.log(res)
            this.isLoginForm = true;
          })
          .catch((err) => {
            this.errorMsg = err.message
          })
    },
    userLogin(){
      if (!this.validateUser()) return;
      axios.post(`${this.$restip}/login`, {
        username: this.form.username,
        password: this.form.password
      })
          .then((res) => {
            localStorage.setItem("auth", res.headers.authorization)
            localStorage.setItem("roles", res.headers.role)
            this.$router.push("/Lingo")
          })
          .catch((err) => {
            this.errorMsg = err.message
          })
    }
  }
}
</script>

<style scoped>
.page-container{
  display: flex;
  justify-content: center;
  padding-top: 60px;
}

form{
  min-width: 350px;
}
</style>