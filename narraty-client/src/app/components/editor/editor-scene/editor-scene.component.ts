import { Input, Output, EventEmitter, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EditorIndexSwitcherComponent } from '../editor-index-switcher/editor-index-switcher.component';
import { Scene } from '../../../pages/editor/editor.page';
import { EditStoryService } from '../../../business/services/story.service';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-editor-scene',
  standalone: true,
  imports: [CommonModule, EditorIndexSwitcherComponent, FormsModule],
  templateUrl: './editor-scene.component.html',
  styleUrl: './editor-scene.component.scss',
})
export class EditorSceneComponent {

  @Input() scene: Scene | undefined;
  @Input() sceneIds: string[] = [];
  @Input() currentSceneId: string = '';

  @Output() sceneChange = new EventEmitter<string>();
  @Output() sceneTextChange = new EventEmitter<string>();
  @Output() sceneResultChange = new EventEmitter<number>();

  @Output() addScene = new EventEmitter<void>();
  constructor(private editStoryService: EditStoryService) {}
  private textChangeSubject = new Subject<string>();

  ngOnInit() {
    this.textChangeSubject.pipe(
      debounceTime(200) 
    ).subscribe((newText) => {
      this.saveTextChange(newText);
    });
  }
  
  private saveTextChange(newText: string) {
    if (!this.scene || !this.scene.id) {
      console.warn('Aucun ID de scène trouvé, abandon');
      return;
    }
  
    this.editStoryService.editSceneText(this.scene.id, newText).subscribe({
      next: (res) => console.log('Texte sauvegardé avec succès', res),
      error: (err) => console.error('Erreur API', err)
    });
  }
  
  onTextChange(newText: string) {

    this.textChangeSubject.next(newText);
  }

  private saveResultChange(newResult: number) {
    if (!this.scene || !this.scene.id) {
      console.warn('Aucun ID de scène trouvé, abandon');
      return;
    }
  
    this.editStoryService.editSceneStatus(this.scene.id, newResult).subscribe({
      next: (res) => console.log('Statut sauvegardé avec succès', res),
      error: (err) => console.error('Erreur API', err)
    });
  }
  onResultChange(newResult: number) {

    this.saveResultChange(newResult);
  }
}
