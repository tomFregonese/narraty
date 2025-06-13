import { Component, OnInit } from '@angular/core';
import { MainBlockComponent } from "../../components/main-block/main-block.component";
import { NavbarComponent } from "../../components/navbar/navbar.component";
import { TaleCardComponent } from "../../components/tale-card/tale-card.component";
import { EditStoryService } from '../../business/services/story.service';
import { EditTale, ReadTale } from '../../business/models/tale.model';
import { TalesCardEditComponent } from "../../components/tales-card-edit/tales-card-edit.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tales-dashboard',
  imports: [MainBlockComponent, NavbarComponent, TalesCardEditComponent,CommonModule],
  templateUrl: './tales-dashboard.component.html',
  styleUrl: './tales-dashboard.component.scss'
})
export class TalesDashboardComponent implements OnInit {

  myTales: EditTale[] = [];

  constructor(private storyService: EditStoryService) {}
  ngOnInit(): void {
    this.loadMyTales();
  }

  loadMyTales(): void {
    this.storyService.getAllMyTales().subscribe({
      next: (tales) => {
        this.myTales = tales;
      },
      error: (err) => {
        console.error('Erreur de chargement des aventures', err);
      }
    });
  }

  createTale(): void {
    this.storyService.createTale().subscribe({
      next: (newTale) => {
        this.myTales.push(newTale);
      },
      error: (err) => {
        console.error('Erreur lors de la création de l\'aventure', err);
      }
    });
  }
}
