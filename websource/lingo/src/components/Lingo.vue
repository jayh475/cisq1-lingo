<template>

  <div>

    <button v-on:click="startGame">Start game</button>
    <P>{{ lingoGameData }}</p>


    <div class="game">

      <input class="form-control" v-model="attempt" name="guess" placeholder="guess">
      <button class="btn" v-on:click="makeGuess">guess</button>
      <p>Guess is:{{ attempt }}</p>
      <p> game Id = {{ this.gameId }}</p>


      <div v-if="lingoGameData">
        <div v-for="feedback in lingoGameData.feedbacks" :key="feedback" class="row">
          <div :class="getClassForLetter(feedback.marks[x])" v-for="(letter,x) in feedback.attempt.split('')"
               :key="letter">{{ letter }}
          </div>
        </div>
      </div>


    </div>


    <button v-on:click="nextRound">Volgende ronde</button>


  </div>


</template>


<script>
import axios from 'axios';
// import _ from 'lodash';


export default {
  name: "Lingo",

  data() {
    return {
      lingoGameData: null,
      attempt: null,
      gameId: null
    }
  },
  methods: {
    startGame() {
      axios.post(`${this.$restip}/lingoGame/start`,{},this.$auth()).then((response) => {
        this.lingoGameData = response.data;
        this.gameId = response.data.gameId;
        console.log(response.data);


      })
          .catch(error => alert(error))

    },
    nextRound() {
      axios.post(`${this.$restip}/lingoGame/${this.gameId}/newRound`,{},this.$auth()).then((response) => {
        this.lingoGameData = response.data;
        this.gameId = response.data.gameId;
        console.log(response.data);


      })
          .catch(error => alert(error))

    },
    makeGuess() {
      axios.post(`${this.$restip}/lingoGame/${this.gameId}/guess`,{attempt: this.attempt},this.$auth()
      ).then((response) => {
        this.lingoGameData = response.data
        console.log(response);
      })
          .catch(error => alert(error))
    },
    getClassForLetter(mark) {
      if (mark === "CORRECT") {
        return "box box-correct"
      }
      if (mark === "ABSENT") {
        return "box box-absent"
      }
      if (mark === "PRESENT") {
        return "box box-present"
      }

    }

  }

}

</script>


<style>


.form-control {
  display: inline-block;
}

.btn {
  display: inline-block;
}

.row {
  margin: auto;
  width: 50%;
  display: flex;
}

.box {
  border: solid;
  min-width: 34px;
  border-radius: 4px;
  text-transform: capitalize;


}

.box-correct {
  background: green;
}

.box-absent {
  background: none;
}

.box-present {
  background: orange;

}


.item-a {
  grid-area: header;
}

.item-b {
  grid-area: game;
}

.item-c {
  grid-area: sidebar;
}

.item-d {
  grid-area: footer;
}

.container {
  display: grid;
  grid-template-columns: 50px 50px 50px 50px;
  grid-template-rows: auto;
  grid-template-areas:
    "header header header header"
    ". game game ."
    "footer footer footer ";
}


</style>