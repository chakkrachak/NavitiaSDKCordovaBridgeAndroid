import { Component, NgZone } from '@angular/core';
import { NavController } from 'ionic-angular';

declare var NavitiaSDK: any;

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
	items;

	constructor(public navCtrl: NavController, private zone: NgZone) {
		this.initializeItems();
	}

	initializeItems() {
	    this.items = [
	      'Amsterdam',
	      'Bogota',
	      'Buenos Aires',
	      'Cairo',
	      'Dhaka',
	      'Edinburgh',
	      'Geneva',
	      'Genoa',
	      'Glasglow',
	      'Hanoi',
	      'Hong Kong',
	      'Islamabad',
	      'Istanbul',
	      'Jakarta',
	      'Kiel',
	      'Kyoto',
	      'Le Havre',
	      'Lebanon',
	      'Lhasa',
	      'Lima',
	      'London',
	      'Los Angeles',
	      'Madrid',
	      'Manila',
	      'New York',
	      'Olympia',
	      'Oslo',
	      'Panama City',
	      'Peking',
	      'Philadelphia',
	      'San Francisco',
	      'Seoul',
	      'Taipeh',
	      'Tel Aviv',
	      'Tokio',
	      'Uelzen',
	      'Washington'
	    ];
	  }

	getItems(ev) {
		// Reset items back to all of the items
		this.initializeItems();

		// set val to the value of the ev target
		var val = ev.target.value;

		// if the value is an empty string don't filter the items
		if (val && val.trim() != '') {
		  this.items = this.items.filter((item) => {
		    return (item.toLowerCase().indexOf(val.toLowerCase()) > -1);
		  })
		}

		this.popEcho(val);
	}

	popEcho(textToDisplay) {
		var that = this;
		NavitiaSDK.placesGetPlaces(textToDisplay,
			function(placesResponse) {
				that.zone.run(function () {
                   	that.items.push(placesResponse);
                })
			},
			function(err) {

			});
	}
}
