'use strict';

// selecting elements:

const modal = document.querySelector('.modal');
const overlay = document.querySelector('.overlay');
const btnCloseModal = document.querySelector('.btn--close-modal');
const btnsOpenModal = document.querySelectorAll('.btn--show-modal');
const btnScroll = document.querySelector('.btn--scroll-to');
const section1 = document.querySelector('#section--1');
// // // //
const navEl = document.querySelectorAll('.nav__link');
const navLinks = document.querySelector('.nav__links');
const navBar = document.querySelector('.nav');

// modal window:

const openModal = function () {
  modal.classList.remove('hidden');
  overlay.classList.remove('hidden');
};

const closeModal = function () {
  modal.classList.add('hidden');
  overlay.classList.add('hidden');
};

btnsOpenModal.forEach(btn => btn.addEventListener('click', openModal));
btnCloseModal.addEventListener('click', closeModal);
overlay.addEventListener('click', closeModal);

document.addEventListener('keydown', function (e) {
  if (e.key === 'Escape' && !modal.classList.contains('hidden')) {
    closeModal();
  }
});

// smooth scrolling:

btnScroll.addEventListener('click', function (event) {
  let s1coords = section1.getBoundingClientRect();

  // an old way: window.scroll(), window.scrollTo();
  //   window.scrollTo({
  //     left: s1coords.left + window.pageXOffset,
  //     top: s1coords.top + window.pageYOffset,
  //     behavior: 'smooth',
  //   });

  // more modern way: Element.scrollIntoView();
  section1.scrollIntoView({
    behavior: 'smooth',
  });
});

// Adding page navigation using event delegation:

navBar.addEventListener('click', function (event) {

  if (
    event.target.classList.contains('nav__link') &&
    !event.target.classList.contains('nav__link--btn')
  ) {
    const id = event.target.getAttribute('href');
    console.log(id);
    document.querySelector(id).scrollIntoView({
      behavior: 'smooth',
    });
  }
});

// Tabbed component:

const opTabs = document.querySelectorAll('.operations__tab');
// console.log(opTabs);
const opTabContainer = document.querySelector('.operations__tab-container');
// console.dir(opTabContainer);
const opContent = document.querySelectorAll('.operations__content');
// console.log(opContent);

// Clicking buttons - animation, clicking buttons - changing appearance of div content (adding and removing classes)
//  - не назначая обработчик на каждую кнопку - так не оч чистый код, лучше - делегированием этого события на родительский элемент.
// event delegation вместо назначения одинаковых функций каждой кнопке.

opTabContainer.addEventListener('click', function (e) {
  e.preventDefault();

  const clicked = e.target.closest('.operations__tab');
  if (!clicked) return; // checking if clicked is null;

  // changing classes at buttons to active (firstly remove active from all);
  opTabs.forEach(t => t.classList.remove('operations__tab--active'));
  clicked.classList.add('operations__tab--active');

  //activate content area - matching button(clicked) data attribute and class of content area:

  opContent.forEach(c => {
    c.classList.remove('operations__content--active');
  });

  document
    .querySelector(`.operations__content--${clicked.dataset.tab}`)
    .classList.add('operations__content--active');
});

// Menu fade animation:

const handleHover = function (e) {
  if (e.target.classList.contains('nav__link')) {
    const link = e.target;
    // traversing DOM-tree to choose all of the sibling elements of "link":
    const siblings = link.closest('.nav').querySelectorAll('.nav__link'); // nodeList
    const logo = link.closest('.nav').querySelector('.nav__logo');

    // change opacity of all links exept for the targeted one:
    siblings.forEach(el => {
      if (el !== link) {
        el.style.opacity = this;
      }
      logo.style.opacity = this;
    });
  }
};

// binding argument (opacity value) as a context to a handler function:
navBar.addEventListener('mouseover', handleHover.bind(0.5));
navBar.addEventListener('mouseout', handleHover.bind(1));

// Making navBar sticky on scroll:

window.addEventListener('scroll', stickyNavFunc);

function stickyNavFunc() {
  let coords = section1.getBoundingClientRect();
  if (window.scrollY > coords.top + window.scrollY) {
    navBar.classList.add('sticky');
  } else {
    navBar.classList.remove('sticky');
  }
}

// Revealing sections on scroll with IntersectionObserver API

const allSections = document.querySelectorAll('.section');

const revealSection = function (entries, observer) {
  const entry = entries[0];

  if (!entry.isIntersecting) return; //if doesn't intersecting - do nothing

  entry.target.classList.remove('section--hidden');
  observer.unobserve(entry.target); // after revealing section - unobserve;
};

const sectionObserver = new IntersectionObserver(revealSection, {
  // callBack funcion and options object;
  root: null, // null = viewport
  threshold: 0.15, //threshold value at which to observe the intersection between target el and viewport
});

allSections.forEach(section => {
  sectionObserver.observe(section);
  section.classList.add('section--hidden');
});

// Lazy load images using IntersectionObserver API:

const imagesLazy = document.querySelectorAll('img[data-src]');
const loadingImg = function (entries, observer) {
  const entry = entries[0];
  if (!entry.isIntersecting) return;
  // changing source of the image and deleting class that is bluring the lazy image:
  entry.target.src = entry.target.dataset.src;

  entry.target.addEventListener('load', function () {
    entry.target.classList.remove('lazy-img');
  });
  //unobserve an element after the function is called:
  observer.unobserve(entry.target);
};

const imgObserver = new IntersectionObserver(loadingImg, {
  root: null,
  threshold: 0,
  rootMargin: '200px',
});

imagesLazy.forEach(img => imgObserver.observe(img));

// building a slider component:

const sliderFunc = function () {
  const slides = document.querySelectorAll('.slide');
  const slider = document.querySelector('.slider');
  const btnLeft = document.querySelector('.slider__btn--left');
  const btnRight = document.querySelector('.slider__btn--right');
  const dotContainer = document.querySelector('.dots');
  let currSlide = 0;
  let maxSlide = slides.length;

  const goToSlide = function (slide) {
    slides.forEach((s, i) => {
      s.style.transform = `translateX(${(i - slide) * 100}%)`;
    });
  };

  const createDots = function () {
    slides.forEach(function (_, i) {
      dotContainer.insertAdjacentHTML(
        'beforeend',
        `<button class="dots__dot" data-slide=${i}></button>`
      );
    });
  };

  const activateDots = function (slide) {
    document
      .querySelectorAll('.dots__dot')
      .forEach(dot => dot.classList.remove('dots__dot--active'));

    document
      .querySelector(`.dots__dot[data-slide="${slide}"]`)
      .classList.add('dots__dot--active');
  };

  const init = function () {
    goToSlide(0);
    createDots();
    activateDots(0);
  };

  //initializing slider:
  init();

  const nextSlide = function () {
    if (currSlide === maxSlide - 1) {
      currSlide = 0;
    } else {
      currSlide++;
    }
    goToSlide(currSlide);
    activateDots(currSlide);
  };

  const prevSlide = function () {
    if (currSlide === 0) {
      currSlide = maxSlide - 1;
    } else {
      currSlide--;
    }
    goToSlide(currSlide);
    activateDots(currSlide);
  };

  // Event handlers:
  btnRight.addEventListener('click', nextSlide);
  btnLeft.addEventListener('click', prevSlide);

  document.addEventListener('keydown', function (e) {
    if (e.key === 'ArrowRight') {
      nextSlide();
    }
    if (e.key === 'ArrowLeft') {
      prevSlide();
    }
  });

  dotContainer.addEventListener('click', function (e) {
    if (e.target.classList.contains('dots__dot')) {
      let slideNo = e.target.dataset.slide;
      goToSlide(slideNo);
      activateDots(slideNo);
    }
  });
};

sliderFunc();

///// ---------------- //////// -----------------////////

// Practise at event bubbling and capturing to understand differences between event.target | this ( event.currentTarget)
// example: changing backgroundColor of Nav menu elements by clicking on them:

// const randomInt = function (min, max) {
//   return Math.trunc(Math.random() * max - min + 1);
// };

// const randomColor = function () {
//   return `rgb(${randomInt(0, 255)},${randomInt(0, 255)},${randomInt(0, 255)})`;
// };

// navBar.addEventListener('click', function (e) {
//   e.preventDefault();
//   this.style.backgroundColor = randomColor();
//   console.log('this', this);
//   console.log('e.target', e.target);
// });

// navLinks.addEventListener('click', function (e) {
//   e.preventDefault();
//   this.style.backgroundColor = randomColor();
//   console.log('this', this);
//   console.log('e.target', e.target);
// });

// navEl.addEventListener('click', function (e) {
//   e.preventDefault();
//   this.style.backgroundColor = randomColor();
//   console.log('this', this);
//   console.log('e.target', e.target);
// });
