import { Component } from '@angular/core';
import { MainBlockComponent } from "../../components/main-block/main-block.component";
import { DisplayStoryService } from '../../business/services/story.service';
import { LinkButtonComponent } from "../../components/link-button/link-button.component";
import { TaleCardComponent } from "../../components/tale-card/tale-card.component";
import { ReadTale } from '../../business/models/tale.model';
import { NavbarComponent } from "../../components/navbar/navbar.component";

@Component({
  selector: 'app-explore',
  imports: [MainBlockComponent, TaleCardComponent, NavbarComponent],
  templateUrl: './explore.page.html',
  styleUrl: './explore.page.scss'
})
export class ExplorePage {
  protected stories: ReadTale[] = [];

  constructor(private displayStoryService: DisplayStoryService) { }

  ngOnInit() {
    this.displayStoryService.getAllTales().subscribe({
      next: (tales) => {
        this.stories = tales;
        console.log('Fetched tales:', this.stories);
      },
      error: (err) => {
        console.error('Error fetching tales:', err);
      }
    });
  }
}
