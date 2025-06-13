import { Component } from '@angular/core';
import { TaleNavComponent } from '../../components/tales/tale-nav/tale-nav.component';
import { MainBlockComponent } from '../../components/main-block/main-block.component';
import { TaleContentComponent } from '../../components/tales/tale-content/tale-content.component';
import {PlayStoryService} from '../../business/services/story.service';
import {ActivatedRoute} from '@angular/router';
import {ReadScene} from '../../business/models/scene.model';

@Component({
  selector: 'app-tale',
  imports: [MainBlockComponent, TaleNavComponent, TaleContentComponent],
  templateUrl: './tale.page.html',
  styleUrl: './tale.page.scss',
})
export class TalePage {

    protected taleId: string = '';
    protected taleTitle: string = 'Merde';
    protected scene: ReadScene = {
        title: '',
        text: '',
        choices: [],
    };

    constructor (private playStoryService: PlayStoryService, private route: ActivatedRoute) {

        this.taleId = this.route.snapshot.paramMap.get('taleId') || '';
        this.playStoryService.readStory(this.taleId).subscribe((scene: ReadScene) => {
            this.scene = scene;
            console.debug('Loaded Tale:', this.scene);
        });

    }

    makeAChoice(choiceId: string) {
          this.playStoryService.makeAChoice(this.taleId, choiceId).subscribe((scene: ReadScene) => {
              this.scene = scene;
              console.debug('Loaded Tale:', this.scene);
          });
    }

}
