" more configs in /etc/vimrc and /usr/share/vim/vimfils

set nocompatible
set number
set t_Co=256
set background=dark

" new stuff, aug 21 2014
" set ttyfast   " this might improve performance, iono
set showmatch   " matching brackets and stuff
set scrolloff=5 " keep 10 lines of context above and below cursor

" colours after 80 chars
" set colorcolumn=81
" set columns=81
" whatever
" end of new stuff, aug 21 2014

set cursorline
" highlight Cursorline cterm=bold ctermbg=Black
highlight Cursorline cterm=bold
" highlight Cursorline cterm=bold ctermbg=Black ctermfg=White
highlight LineNr ctermfg=Green

" set hlsearch
if has("syntax")
    syntax on
    "set textwidth=80
else
    set spell
    "set wrapmargin=14
endif

filetype indent on
set wrap linebreak
"set autoindent
set smartindent

" prevents vim from garbling pasted text with indentation
" also seems to cause freezes though...
" set paste

" this doesn't seem to work...
"map <MiddleMouse> <Nop>

" i think i meant "command Q q" because i wanted to quit with :Q
" it probably had nothing to do with macros
" it was probably to stop accidentally entering ex mode
" nnoremap Q q
nnoremap Y y$

nnoremap Q :
" command Q q
" command W w
" command was causing vim to explode upon reloading $MYVIMRC
nnoremap :W :w
nnoremap :Q :q

" these are all set in /etc/vimrc anyway...
" no they aren't
set expandtab
set tabstop=4
set sw=4

"this stuff is also set in /usr/share/vim/vimfiles/archlinux.vim
"allow backspacing over everything in insert mode
set backspace=indent,eol,start
" Suffixes that get lower priority when doing tab completion for filenames.
" These are files we are not likely to want to edit or read.
" set suffixes=.bak,~,.swp,.o,.info,.aux,.log,.dvi,.bbl,.blg,.brf,.cb,.ind,.idx,.ilg,.inx,.out,.toc,.png,.jpg

" set history=20

" by default, vim keeps backup files in the same dir as the working file.
" much better to keep them somewhere else, yes?
set backupdir=~/.vim/backups
set directory=~/.vim/backups

" The following are commented out as they cause vim to behave a lot
" differently from regular Vi. They are highly recommended though.
set showcmd            " Show (partial) command in status line.
" set showmatch          " Show matching brackets.
set ignorecase         " Do case insensitive matching
set smartcase          " Do smart case matching
set incsearch          " Incremental search
" set autowrite          " Automatically save before commands like :next and :make
" set hidden             " Hide buffers when they are abandoned
set mouse=nv            " Enable mouse usage, but not in insert mode

" compile and display a latex file
" noremap <c-b> :! pdflatexandevince % <CR> <CR>

" abbreviations, these are pretty cool
" set paste breaks these
" abbreviate Wall {-# OPTIONS_GHC -Wall #-}
" abbreviate LANGUAGE {-# LANGUAGE #-}
abbreviate pln System.out.println(

" programming languages
" noremap gb :! mkplcc <CR>

" compile, run a java pragrm
" noremap gb :! ja % <CR>
" noremap gb :! javac *.java && java Scanner <CR>

" gradle build, gb is perfect
noremap gb :! ./gradlew build <CR>
set ruler

" automatically comment out lines 
au FileType haskell,vhdl,ada let b:comment_leader = '-- '
au FileType c,cpp,java,javascript let b:comment_leader = '// '
au FileType bash,sh,python,perl,make,conf let b:comment_leader = '# '
au FileType vim let b:comment_leader = '" '
au FileType tex let b:comment_leader = '% '
au FileType sax let b:comment_leader = '; '
if !exists("b:comment_leader")
    set filetype=java
    let b:comment_leader = '// '
endif
" noremap <silent> g/ :<C-B>sil <C-E>s/^/<C-R>=escape(b:comment_leader,'\/')<CR>/<CR>:noh<CR>
" noremap <silent> g- :<C-B>sil <C-E>s/^\V<C-R>=escape(b:comment_leader,'\/')<CR>//e<CR>:noh<CR>
noremap g/ :<C-B>sil <C-E>s/^/<C-R>=escape(b:comment_leader,'\/')<CR>/<CR>:noh<CR>
noremap g- :<C-B>sil <C-E>s/^\V<C-R>=escape(b:comment_leader,'\/')<CR>//e<CR>:noh<CR>

" set paste " this seems to help on the lab computers
" setting paste always breaks a buncha things. pastetoggle is way better!
nnoremap <F2> :set invpaste paste?<CR>
set pastetoggle=<F2>
set showmode

nnoremap <F3> :set filetype=java<CR>
nnoremap <F12> :so $MYVIMRC<CR>
nnoremap <F4> :diffu<CR>
