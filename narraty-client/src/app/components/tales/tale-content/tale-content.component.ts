import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaleChoiceComponent } from '../tale-choice/tale-choice.component';
import {ReadScene} from '../../../business/models/scene.model';

@Component({
    selector: 'app-tale-content',
    standalone: true,
    imports: [CommonModule, TaleChoiceComponent],
    templateUrl: './tale-content.component.html',
    styleUrl: './tale-content.component.scss',
})
export class TaleContentComponent {
    @Input() scene: ReadScene = { title: '', text: '', choices: [] };
    @Output() choiceIdSelected = new EventEmitter<string>();

    selectChoice(choiceId: string) {
        this.choiceIdSelected.emit(choiceId);
    }
}
