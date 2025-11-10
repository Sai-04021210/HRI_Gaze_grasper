import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { Leaderboard } from './app/leaderboard.component';

bootstrapApplication(Leaderboard, appConfig)
  .catch((err) => console.error(err));
