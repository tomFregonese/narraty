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
  selector: 'app-aventures',
  imports: [MainBlockComponent, LinkButtonComponent, NavbarComponent, CommonModule],
  templateUrl: './aventures.page.html',
  styleUrl: './aventures.page.scss'
})
export class AventuresPage implements OnInit {
  protected aventure: ReadTale | null = null;
  protected aventureId: string = '';
  protected error: boolean = false;
  protected errorMessage: string = '';
  isConnected: boolean = false;

  constructor(private route: ActivatedRoute, private DisplayStoryService: DisplayStoryService, private authService: AuthService) { }

  ngOnInit() {
    this.isConnected = this.authService.isLoggedIn();

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.aventureId = id;
      this.DisplayStoryService.getTaleById(this.aventureId).subscribe({
        next: (tale) => {
          this.aventure = tale;
          console.log('Fetched aventure:', this.aventure);
        },
        error: (err) => {
          this.error = true;
          this.errorMessage = 'Erreur lors de la récupération de l\'aventure';
          console.error('Error fetching aventure:', err);
        }
      });
    } else {
      this.error = true;
      this.errorMessage = 'Aucune aventure trouvée';
    }
  }

  
}
