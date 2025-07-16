import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Role, Rule } from '../model/rule';
import { RulesService } from '../services/rules.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-rules-view',
  templateUrl: './rules-view.component.html',
  styleUrls: ['./rules-view.component.css']
})
export class RulesViewComponent implements OnInit
{
  @Input() readonly = false;
 
  rules: Rule[] = [];
  ruleForm!: FormGroup;
  submitted = false;
  userRole: string | null = null;
 
  constructor(
    private fb: FormBuilder,
    private rulesService: RulesService,
    private authService: AuthService
  ) {}
 
  ngOnInit(): void {
    // Get logged in user role
    this.userRole = this.authService.getCurrentUserRole();
 
    // Initialize form if not readonly
    if (!this.readonly) {
      this.ruleForm = this.fb.group({
        code: ['', Validators.required],
        description: ['', Validators.required],
        targetRole: ['ALL', Validators.required],
        active: [true]
      });
    }
 
    // Load and filter rules
    this.loadRules();
  }
 
  get f() {
    return this.ruleForm?.controls;
  }
 
  loadRules(): void {
    this.rulesService.getRules().subscribe({
      next: (data: Rule[]) => {
        // Show only rules for ALL or current user role
        this.rules = data;
      },
      error: (err: any) => console.error('Failed to load rules:', err)
    });
  }
 
  onSubmit(): void {
    if (this.readonly) return;
 
    this.submitted = true;
    if (this.ruleForm.invalid) {
      this.submitted = false;
      return;
    }
 
    const newRule: Rule = this.ruleForm.value;
 
    this.rulesService.addRule(newRule).subscribe({
      next: (addedRule: Rule) => {
        // Add only if rule targetRole matches user or ALL
        if (addedRule.targetRole === Role.ALL || addedRule.targetRole === this.userRole) {
          this.rules.push(addedRule);
        }
        
        // Reset form with defaults
        this.ruleForm.reset({ active: true, targetRole: 'ALL' });
        this.ruleForm.markAsPristine();
        this.ruleForm.markAsUntouched();
        this.submitted = false;
        alert('Rule added successfully!');
      },
      error: (err: any) => {
        console.error('Error adding rule:', err);
        this.submitted = false;
        alert('Failed to add rule');
      }
    });
  }
 
  deleteRule(id?: number): void {
    if (this.readonly || !id) return;
 
    if (confirm('Are you sure you want to delete this rule?')) {
      this.rulesService.deleteRule(id).subscribe({
        next: () => {
          this.rules = this.rules.filter(rule => rule.id !== id);
          alert('Rule deleted successfully!');
        },
        error: (err: any) => {
          console.error('Error deleting rule:', err);
          alert('Failed to delete rule');
        }
      });
    }
  }

}
