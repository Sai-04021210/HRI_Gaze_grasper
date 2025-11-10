import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-leaderboard',
  imports: [FormsModule, CommonModule],
  templateUrl: './leaderboard.component.html',
  styleUrl: './leaderboard.component.css'
})
export class Leaderboard {
  userInput = '';
  leaderboard: Array<{name: string; score: number}> = [];

  // Enables pressing 'enter' in addition to clicking the button
  onKeydown(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.addEntry()
    }
  }

  // Adds a new entry to the leaderboard
  addEntry() {
    // Remove blankspaces
    const name = this.userInput.trim();
    // If there is no user input
    if (!name) return alert("Please enter a name.");

    // New entry is added to array
    this.leaderboard.push({name, score: this.generateScore()})
    // Sorts entries by score in descending order
    this.leaderboard.sort((a, b) => b.score - a.score);
    // Resets user input
    this.userInput = "";
  }

  // Generates a random score between 1-100
  generateScore(){
    return Math.floor(Math.random() * 100) + 1;
  }
}
