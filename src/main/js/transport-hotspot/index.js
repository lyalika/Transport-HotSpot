import './styles/application.scss';

import mapboxgl from 'mapbox-gl';
import MapboxLanguage from '@mapbox/mapbox-gl-language';
import Awesomplete from 'awesomplete';
import moment from 'moment';
import bg from 'moment/locale/bg';
moment.locale('bg');
document.moment = moment;
moment.updateLocale('bg', {
    relativeTime : {
        future: "след %s",
        past:   "преди %s",
        s  : 'няколко секунди',
        ss : '%d сек',
        m:  "1 мин.",
        mm: "%d мин.",
        h:  "1 ч.",
        hh: "%d ч.",
        d:  "1 д.",
        dd: "%d д.",
        M:  "1 мес.",
        MM: "%d мес.",
        y:  "1 г.",
        yy: "%d г."
    }
});

import {MDCTextField} from '@material/textfield/index';
const textField = new MDCTextField(document.querySelector('.mdc-text-field'));

mapboxgl.accessToken = 'pk.eyJ1IjoiaWduaXNmIiwiYSI6ImNqcWF6eGxhejNnc2Q0M3BwZTZhem80aHYifQ.yo2d2xJ4sPIWVg-OQl5oig';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v9',
    center: [23.3219, 42.6977],
    zoom: 14
});

mapboxgl.setRTLTextPlugin('https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-rtl-text/v0.1.0/mapbox-gl-rtl-text.js');
map.addControl(new MapboxLanguage({
  defaultLanguage: 'mul'
}));

map.on('load', function() {
    map.addSource('stops', {
        type: 'geojson',
        data: '/api/stops'
    });

    map.addLayer({
        id: "unclustered-point",
        type: "circle",
        source: "stops",
        paint: {
            "circle-color": "#11b4da",
            "circle-radius": 4,
            "circle-stroke-width": 1,
            "circle-stroke-color": "#fff"
        }
    });

    map.on('mouseenter', 'unclustered-point', function () {
        map.getCanvas().style.cursor = 'pointer';
    });

    map.on('mouseleave', 'unclustered-point', function () {
        map.getCanvas().style.cursor = '';
    });
    
    // When a click event occurs on a feature in the places layer, open a popup
	// at the
    // location of the feature, with description HTML from its properties.
    map.on('click', 'unclustered-point', function(e) {
    	displayPopup(e.features[0], e);
    });
});

function debounce(func, delay) {
    var inDebounce = void 0;
    return function () {
        var context = this;
        var args = arguments;
        clearTimeout(inDebounce);
        inDebounce = setTimeout(function () {
            return func.apply(context, args);
        }, delay);
    };
};

var popup;

function displayPopup(feature, e = undefined) {
    var coordinates = feature.geometry.coordinates.slice();

    if (e !== undefined) {
        // Ensure that if the map is zoomed out such that multiple
        // copies of the feature are visible, the popup appears
        // over the copy being pointed to.
        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
        }
    }
    
    const url = '/api/stops/'+feature.id;
    fetch(url).then(r => r.json()).then(d => {
        console.log(d);
        const scheduleTemplate = document.querySelector('#schedule');
        const scheduleEntryTemplate = document.querySelector('#schedule-entry');
        const schedule = document.importNode(scheduleTemplate.content, true);
        
        const title = schedule.querySelectorAll("h2")[0];
        title.textContent = d.properties.name + ' (' + d.properties.number + ')';
        const arrivals = schedule.querySelectorAll("ul")[0];
        console.log(d.properties.arrivals);
        Object.keys(d.properties.arrivals).forEach(function(line){
            var lineElement = document.importNode(scheduleEntryTemplate.content, true);
            var lineTitle = lineElement.querySelectorAll(".mdc-list-item__primary-text")[0];
            lineTitle.textContent = line;
            var arrivalsElement = lineElement.querySelectorAll(".mdc-list-item__secondary-text")[0];
            if (d.properties.arrivals[line].length > 0) {
            	arrivalsElement.textContent = d.properties.arrivals[line].map(function(arrivalTime){
            		return moment(arrivalTime).toNow(true);
            	}).join(', ');
            	// arrivalsElement.textContent = "hello";
            } else {
            	arrivalsElement.textContent = "Няма следващо превозно средство";
            }
            arrivals.appendChild(lineElement);
        });
        
        const div = document.createElement('div');
        div.appendChild(schedule);
        
        if (popup !== undefined) {
        	popup.remove();
        }
        popup = new mapboxgl.Popup();
        popup.setLngLat(coordinates)
            .setHTML(div.innerHTML)
            .addTo(map);
    });
};
    

const searchInput = document.getElementById("search");
const searchContainer = document.getElementById("autocomplete-container");
const awesomplete = new Awesomplete(searchInput, {
	minChars: 3,
	maxItems: 5,
	list: [],
	container: function (input) {
		return document.getElementById("autocomplete-container");
	},
	item: function(match, input, item_id) {
		const test = document.createElement('li');
		test.classList.add('mdc-list-item');
		console.log(match);
		test.innerHTML = `
              <span class="mdc-list-item__graphic" role="presentation">
                <i class="material-icons" aria-hidden="true">directions_bus</i>
              </span>
              <span class="mdc-list-item__text">
                <span class="mdc-list-item__primary-text">${match.label.name}</span>
                <span class="mdc-list-item__secondary-text">${match.label.lines.map(function(l){return l.name;})}</span>
              </span>
              <a href="#" class="mdc-list-item__meta material-icons" aria-label="Повече информация" title="Повече информация" onclick="event.preventDefault();"> info </a>
	      `;
		return test;
	},
	filter: function (item, input) {
		return true;
	},
	replace: function (text) {
		this.input.value = text.label.name;
	}
});

searchInput.addEventListener("awesomplete-open", function() { searchContainer.hidden = false; });
searchInput.addEventListener("awesomplete-close", function() { searchContainer.hidden = true; });
searchInput.addEventListener("awesomplete-selectcomplete", (ev) => {
	map.flyTo({center: ev.text.label.coordinates});
	displayPopup(ev.text.label.feature);
});
searchInput.addEventListener('keyup', debounce(function(e) {
    var code = (e.keyCode || e.which);      
    if (code === 37 || code === 38 || code === 39 || code === 40 || code === 27 || code === 13) {
        return;
    } else {
        const murl = '/api/stops?q='+searchInput.value;
        fetch(murl).then(r => r.json()).then(d => {
        	console.log(d);
        	awesomplete.list = d.features.map(feature => ({value: feature.id, label: {
        		name: feature.properties.name + ' (' + feature.properties.number + ')',
        		lines: feature.properties.lines,
        		coordinates: feature.geometry.coordinates,
        		feature: feature
        	}}));
        });
    }
}));
