import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MainBlockComponent } from "../../components/main-block/main-block.component";
import { LinkButtonComponent } from "../../components/link-button/link-button.component";
import { NavbarComponent } from "../../components/navbar/navbar.component";
import { DisplayStoryService } from '../../business/services/story.service';
import { ReadTale } from '../../business/models/tale.model';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../business/services/auth.service';

@Component({
  selector: 'app-tale-preview',
  imports: [MainBlockComponent, LinkButtonComponent, NavbarComponent, CommonModule],
  templateUrl: './tale-preview-page.component.html',
  styleUrl: './tale-preview-page.component.scss'
})
export class TalePreviewPage implements OnInit {
  protected tale: ReadTale | null = null;
  protected taleId: string = '';
  protected error: boolean = false;
  protected errorMessage: string = '';
  isConnected: boolean = false;

  constructor(private route: ActivatedRoute, private DisplayStoryService: DisplayStoryService, private authService: AuthService) { }

  ngOnInit() {
    this.isConnected = this.authService.isLoggedIn();

    this.taleId = this.route.snapshot.paramMap.get('id')!;

    this.DisplayStoryService.getTaleById(this.taleId).subscribe({
      next: (tale) => {
        this.tale = tale;
        console.log('Fetched tale:', this.tale);
      },
      error: (err) => {
        this.error = true;
        this.errorMessage = 'Erreur lors de la récupération de la Tale :(';
        console.error('Error fetching aventure:', err);
      }
    });
  }

  
}
