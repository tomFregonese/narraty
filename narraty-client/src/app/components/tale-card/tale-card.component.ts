import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-tale-card',
  imports: [],
  templateUrl: './tale-card.component.html',
  styleUrl: './tale-card.component.scss'
})
export class TaleCardComponent {

  @Input() id: string = '';
  @Input() title: string = 'Les Montagnes de la folie';

}
