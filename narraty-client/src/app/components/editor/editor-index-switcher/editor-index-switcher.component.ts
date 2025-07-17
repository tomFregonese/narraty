import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-editor-index-switcher',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './editor-index-switcher.component.html',
  styleUrl: './editor-index-switcher.component.scss',
})
export class EditorIndexSwitcherComponent {
  @Input() ids: string[] = [];
  @Input() currentId: string = '';
  @Output() select = new EventEmitter<string>();
  @Output() add = new EventEmitter<void>();

  onAddClick() {
    this.add.emit();
  }
}
