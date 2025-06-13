import { Component } from '@angular/core';
import { MainBlockComponent } from "../../components/main-block/main-block.component";
import { LinkButtonComponent } from "../../components/link-button/link-button.component";

@Component({
  selector: 'app-home',
  imports: [MainBlockComponent, LinkButtonComponent],
  templateUrl: './home.page.html',
  styleUrl: './home.page.scss'
})
export class HomePage {

}
