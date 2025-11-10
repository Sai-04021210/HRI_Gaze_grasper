import { TestBed } from '@angular/core/testing';
import { Leaderboard } from './leaderboard.component';

// Unit Tests

describe('Leaderboard', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Leaderboard],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(Leaderboard);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(Leaderboard);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Hello, leaderboard');
  });
});
