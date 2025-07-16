import { Component, OnInit } from '@angular/core';
import { PropertyType } from '../model/property-type';
import { PlatformConfigService } from '../services/platform-config.service';

@Component({
  selector: 'app-property-categories',
  templateUrl: './property-categories.component.html',
  styleUrls: ['./property-categories.component.css']
})
export class PropertyCategoriesComponent implements OnInit {

  newCategory: PropertyType = { id: 0, name: '', description: '' }; // add 'id' if required
  categories: PropertyType[] = [];
  editingCategory: PropertyType | null = null;

  constructor(private platformConfigService: PlatformConfigService) {}

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    this.platformConfigService.getCategories().subscribe({
      next: (data: PropertyType[]) => (this.categories = data),
      error: (err) => console.error('Failed to load categories:', err)
    });
  }

  addCategory(): void {
    if (!this.newCategory.name) return;

    this.platformConfigService.addCategory(this.newCategory).subscribe({
      next: (added) => {
        this.categories.push(added);
        this.newCategory = { id: 0, name: '', description: '' };
      },
      error: (err) => console.error('Failed to add category:', err)
    });
  }

  startEdit(category: PropertyType): void {
    this.editingCategory = { ...category };
  }

  updateCategory(): void {
    if (!this.editingCategory || !this.editingCategory.id) return;

    this.platformConfigService
      .updateCategory(this.editingCategory.id, this.editingCategory)
      .subscribe({
        next: (updated) => {
          const index = this.categories.findIndex(c => c.id === updated.id);
          if (index > -1) this.categories[index] = updated;
          this.editingCategory = null;
        },
        error: (err) => console.error('Failed to update category:', err)
      });
  }

  deleteCategory(id: number): void {
    this.platformConfigService.deleteCategory(id).subscribe({
      next: () => {
        this.categories = this.categories.filter(c => c.id !== id);
      },
      error: (err) => console.error('Failed to delete category:', err)
    });
  }

  cancelEdit(): void {
    this.editingCategory = null;
  }

}
