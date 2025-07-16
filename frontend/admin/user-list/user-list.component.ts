import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Role, User } from '../model/user';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  roles = Object.values(Role);
  selectedRole: Role | '' = '';
  errorMessage = '';
  successMessage = '';
  showAddUserForm = false;
  showReportDashboard = false;

  userIdToFetch: number | null = null;
  fetchedUser: User | null = null;
  newUser: User = { name: '', email: '', password: '', role: Role.BUYER, active: true };

  constructor(private userService: UserService, private route: ActivatedRoute) {}

  toggleAddUserForm() {
    this.showAddUserForm = !this.showAddUserForm;
    if (this.showAddUserForm) {
      this.showReportDashboard = false;
    }
  }

  toggleReportDashboard() {
    this.showReportDashboard = !this.showReportDashboard;
    if (this.showReportDashboard) {
      this.showAddUserForm = false; // hide add user form when reports are visible
    }
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const add = params['add'];
      this.showAddUserForm = add === 'true';
    });

    this.loadUsers();
  }

  clearMessages() {
    this.errorMessage = '';
    this.successMessage = '';
  }

  loadUsers() {
    this.clearMessages();
    this.userService.getAllUsers().subscribe({
      next: data => this.users = data,
      error: () => this.errorMessage = 'Unable to load users.'
    });
  }

  filterByRole() {
    this.clearMessages();
    if (!this.selectedRole) return this.loadUsers();
    this.userService.getUsersByRole(this.selectedRole as Role).subscribe({
      next: data => this.users = data,
      error: () => this.errorMessage = 'Unable to filter.'
    });
  }

  toggleActive(user: User): void {
    if (!user.id) {
      this.errorMessage = 'Invalid user ID.';
      return;
    }
    const newState = !user.active;

    this.userService.updateUserStatus(user.id, newState).subscribe({
      next: () => {
        user.active = newState;
        this.successMessage = `User ${user.name} is now ${newState ? 'active' : 'inactive'}.`;
      },
      error: err => {
        console.error('Status toggle failed', err);
        this.errorMessage = `Status update failed (${err.status}).`;
      }
    });
  }

  changeRole(event: Event, user: User) {
    this.clearMessages();
    const role = (event.target as HTMLSelectElement).value as Role;
    this.userService.updateUserRole(user.id!, role).subscribe({
      next: () => {
        user.role = role;
        this.successMessage = `User ${user.name}'s role updated to ${role}.`;
      },
      error: () => this.errorMessage = 'Role update failed.'
    });
  }

  addUser() {
    this.clearMessages();
    if (!this.newUser.name || !this.newUser.email || !this.newUser.password) {
      this.errorMessage = 'All fields are required.';
      return;
    }
    this.userService.createUser(this.newUser).subscribe({
      next: user => {
        this.users.push(user);
        this.successMessage = `User ${user.name} created successfully.`;
        this.newUser = { name: '', email: '', password: '', role: Role.BUYER, active: true };
      },
      error: err => {
        console.error('❌ Error creating user:', err);
        this.errorMessage = err?.error?.message || 'User creation failed.';
      }
    });
  }

  deleteUser(user: User) {
    this.clearMessages();
    if (!confirm(`Delete ${user.name}?`)) return;
    this.userService.deleteUser(user.id!).subscribe({
      next: () => {
        this.users = this.users.filter(u => u.id !== user.id);
        this.successMessage = `User ${user.name} deleted successfully.`;
      },
      error: () => this.errorMessage = 'Deletion failed.'
    });
  }

  getUserById(id: number | null): void {
    this.clearMessages();
    this.fetchedUser = null; // reset previous result

    if (id === null || id === undefined) {
      this.errorMessage = 'Please enter a valid user ID.';
      return;
    }

    this.userService.getUserById(id).subscribe({
      next: (user) => {
        this.fetchedUser = user;
        this.successMessage = `User found: ${user.name}`;
        console.log('User details:', user);
      },
      error: () => {
        this.errorMessage = 'User not found.';
      }
    });
  }
}
