import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EditorIndexSwitcherComponent } from '../editor-index-switcher/editor-index-switcher.component';
import { Choice } from '../../../pages/editor/editor.page';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { EditStoryService } from '../../../business/services/story.service';

@Component({
  selector: 'app-editor-choice',
  standalone: true,
  imports: [CommonModule, FormsModule, EditorIndexSwitcherComponent],
  templateUrl: './editor-choice.component.html',
  styleUrl: './editor-choice.component.scss',
})
export class EditorChoiceComponent {
  @Input() choices: Choice[] = [];
  @Input() currentSceneId: string = '';
  @Input() sceneIds: string[] = [];

  @Output() sceneChange = new EventEmitter<string>();
  @Output() addChoice = new EventEmitter<void>();

  get choiceIds(): string[] {
    return this.choices.map((c) => c.id);
  }

  currentChoiceIndex = 0;

  get currentChoice(): Choice | undefined {
    if (this.choices.length === 0) return undefined;
    if (this.currentChoiceIndex >= this.choices.length) {
      this.currentChoiceIndex = 0;
    }
    return this.choices[this.currentChoiceIndex];
  }

  onChoiceSelect(id: string) {
    const index = this.choices.findIndex((choice) => choice.id === id);
    if (index !== -1) {
      this.currentChoiceIndex = index;
    }
  }

  private textChangeSubject = new Subject<string>();

constructor(private editStoryService: EditStoryService) {
  this.textChangeSubject
    .pipe(debounceTime(200))
    .subscribe((newText) => {
      this.saveChoiceTextChange(newText);
    });
}

onChoiceTextChange(newText: string) {

  this.textChangeSubject.next(newText);
}

private saveChoiceTextChange(newText: string) {
  if (!this.currentChoice) {
    console.warn('Aucun choix courant, abandon');
    return;
  }

  this.editStoryService.editChoiceText(this.currentChoice.id, newText).subscribe({

    error: (err) => console.error('Erreur API', err)
  });
}
selectedTargetSceneId: string | null = null;
onChoiceTargetSceneChange(newTargetSceneId: string | null) {
  if (!this.currentChoice) {
    return;
  }

  this.editStoryService.editChoiceTargetScene(this.currentChoice.id, newTargetSceneId).subscribe({
    next: (res) => console.log('Choix mis à jour avec succès', res),
    error: (err) => console.error('Erreur lors de la mise à jour du choix', err)
  });
}
}
