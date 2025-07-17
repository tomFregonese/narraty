import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-tale-choice',
  standalone: true,
  imports: [],
  templateUrl: './tale-choice.component.html',
  styleUrl: './tale-choice.component.scss',
})
export class TaleChoiceComponent {
  @Input() text!: string;
  @Input() index!: number;
  @Output() clicked = new EventEmitter<void>();

  handleClick(event: Event) {
    event.preventDefault();
    this.clicked.emit();
  }
}
