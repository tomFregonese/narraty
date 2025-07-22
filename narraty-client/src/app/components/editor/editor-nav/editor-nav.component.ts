import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LinkButtonComponent } from '../../link-button/link-button.component';

@Component({
  selector: 'app-editor-nav',
  standalone: true,
  imports: [CommonModule, LinkButtonComponent],
  templateUrl: './editor-nav.component.html',
  styleUrl: './editor-nav.component.scss',
})
export class EditorNavComponent {
  @Input() taleTitle: string = '';
  @Input() taleDescription: string = '';
  @Input() taleCoverUrl: string = '';
  @Output() openPopup = new EventEmitter<void>();
  @Output() publishTale = new EventEmitter();
  @Output() archiveTale = new EventEmitter();
  @Output() deletedTale = new EventEmitter();


  togglePopup() {
    this.openPopup.emit();
  }

  clickPublish() {
    this.publishTale.emit();
  }

  clickArchive() {
    this.archiveTale.emit();
  }

  clickDelete() {
    this.deletedTale.emit();
  }
}
