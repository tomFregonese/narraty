import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { Tale } from '../../../pages/editor/editor.page';
import { EditStoryService } from '../../../business/services/story.service';

@Component({
  selector: 'app-editor-tale',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editor-tale.component.html',
  styleUrls: ['./editor-tale.component.scss'],
})
export class EditorTaleComponent {
  @Input() tale!: Tale;
  @Output() close = new EventEmitter<void>();

  private titleChangeSubject = new Subject<string>();
  private descriptionChangeSubject = new Subject<string>();

  constructor(private editStoryService: EditStoryService) {}

  ngOnInit() {
    this.titleChangeSubject.pipe(debounceTime(200)).subscribe((newTitle) => {
      this.submitTitle(newTitle);
    });

    this.descriptionChangeSubject.pipe(debounceTime(200)).subscribe((newDescription) => {
      this.saveDescriptionChange(newDescription);
    });
  }

  private submitTitle(newTitle: string) {
    if (!this.tale || !this.tale.id) {

      return;
    }

    this.editStoryService.editStoryTitle(this.tale.id, newTitle).subscribe({
      next: (res) => {

        this.tale.title = newTitle;
      },
      error: (err) => {
        console.error('Erreur API', err);
      },
    });
  }

  private saveDescriptionChange(newDescription: string) {
    if (!this.tale || !this.tale.id) {
      console.warn('Aucun ID de tale trouvé, abandon');
      return;
    }

    this.editStoryService.editStoryDescription(this.tale.id, newDescription).subscribe({
      next: (res) => {

        this.tale.description = newDescription;
      },
      error: (err) => {
        console.error('Erreur API', err);
      },
    });
  }

  onTitleChange(newTitle: string) {

    this.titleChangeSubject.next(newTitle);
  }

  onDescriptionChange(newDescription: string) {

    this.descriptionChangeSubject.next(newDescription);
  }

  closePopUp(): void {
    this.close.emit();
  }
}